package com.thmanyah.cms_service.episode.service;

import com.thmanyah.cms_service.episode.dto.EpisodeDto;

public interface EpisodeService {

    Long addNewEpisode(EpisodeDto episodeDto);

    Long updateEpisode(EpisodeDto episodeDto);
}
