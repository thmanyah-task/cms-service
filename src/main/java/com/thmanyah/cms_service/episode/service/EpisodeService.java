package com.thmanyah.cms_service.episode.service;

import com.thmanyah.cms_service.episode.dto.EpisodeDto;

import java.util.List;

public interface EpisodeService {

    List<Long> addNewEpisodes(List<EpisodeDto> episodeDto);

    Long updateEpisode(EpisodeDto episodeDto);

    EpisodeDto findEpisodeById(Long episodeId);
}
