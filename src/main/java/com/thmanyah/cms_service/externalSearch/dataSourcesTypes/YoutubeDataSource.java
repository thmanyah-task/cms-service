package com.thmanyah.cms_service.externalSearch.dataSourcesTypes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thmanyah.cms_service.episode.dto.EpisodeDto;
import com.thmanyah.cms_service.externalSearch.shared.ExternalDataSource;
import com.thmanyah.cms_service.programme.dto.ProgrammeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class YoutubeDataSource implements ExternalDataSource {


    @Value("${youtube.api.key}")
    private String apiKey;
    @Value("${youtube.search.playlists.url}")
    private String playLists;
    @Value("${youtube.search.episodes.url}")
    private String episodesApiUrl;
    @Value("${youtube.search.channels.url}")
    private String youtubeSearchChannel;


    private final ObjectMapper mapper = new ObjectMapper();

    private String fetchFirstChannelIdByQuery(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String searchUrl = String.format(
                    "%s?part=snippet&q=%s&type=channel&maxResults=5&key=%s",
                    youtubeSearchChannel,
                    encodedQuery,
                    apiKey
            );

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(searchUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(response.body());
            JsonNode items = root.get("items");

            if (items != null && items.isArray() && items.size() > 0) {
                JsonNode firstItem = items.get(0);
                JsonNode channelIdNode = firstItem.get("id").get("channelId");
                if (channelIdNode != null) {
                    return channelIdNode.asText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<ProgrammeDto> fetchData(String searchQuery) {
        List<ProgrammeDto> programmes = new ArrayList<>();
        try {
            String channelId = fetchFirstChannelIdByQuery(searchQuery);
            String url = String.format("%s?key=%s&channelId=%s&type=playlist&part=snippet&maxResults=50",
                    playLists, apiKey, channelId);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode playlistsJson = mapper.readTree(response.body()).get("items");

            for (JsonNode playlist : playlistsJson) {
                String playlistId = playlist.get("id").get("playlistId").asText();
                JsonNode snippet = playlist.get("snippet");

                ProgrammeDto programme = new ProgrammeDto();
                programme.setId(null);
                programme.setCreatedDate(LocalDateTime.now());
                String publishedAt = snippet.get("publishedAt").asText();
                LocalDate publishedDate = LocalDate.parse(publishedAt.substring(0, 10));
                programme.setPublishedDate(publishedDate);
                JsonNode thumbnailsNode = snippet.get("thumbnails");
                if (thumbnailsNode != null && thumbnailsNode.has("default")) {
                    String thumbnailUrl = thumbnailsNode.get("default").get("url").asText();
                    programme.setThumbnail(thumbnailUrl);
                }
                programme.setUpdatedDate(LocalDateTime.now());
                programme.setSubject(snippet.get("title").asText());
                programme.setDescription(snippet.get("description").asText());
                programme.setProgrammeUrl("https://www.youtube.com/playlist?list=" + playlistId);

                List<EpisodeDto> episodes = fetchEpisodes(playlistId);
                programme.setEpisodeDtoList(episodes);

                programmes.add(programme);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return programmes;
    }

    private List<EpisodeDto> fetchEpisodes(String playlistId) {
        List<EpisodeDto> episodes = new ArrayList<>();
        try {
            String url = String.format("%s?part=snippet&maxResults=50&playlistId=%s&key=%s",
                    episodesApiUrl, playlistId, apiKey);
            System.out.println("Final Episode Fetch URL: " + url);


            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode items = mapper.readTree(response.body()).get("items");

            AtomicInteger episodeCounter = new AtomicInteger(1);
            for (JsonNode item : items) {
                JsonNode snippet = item.get("snippet");
                String videoId = snippet.get("resourceId").get("videoId").asText();

                EpisodeDto ep = new EpisodeDto();
                ep.setId(null);
                ep.setCreatedDate(LocalDateTime.now());
                ep.setUpdatedDate(LocalDateTime.now());
                ep.setPublishedDate(parseDate(snippet.get("publishedAt").asText()));
                ep.setEpisodeNumber(episodeCounter.getAndIncrement());
                ep.setSubject(snippet.get("title").asText());
                ep.setDescription(snippet.get("description").asText());
                ep.setDuration(1.0);
                JsonNode thumbnailsNode = item.get("snippet").get("thumbnails");
                if (thumbnailsNode != null && thumbnailsNode.has("default")) {
                    String thumbnailUrl = thumbnailsNode.get("default").get("url").asText();
                    ep.setThumbnail(thumbnailUrl);
                }
                ep.setEpisodeUrl("https://www.youtube.com/watch?v=" + videoId);
                episodes.add(ep);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodes;
    }

    private LocalDate parseDate(String isoDate) {
        try {
            return OffsetDateTime.parse(isoDate).toLocalDate();
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
