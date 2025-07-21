package com.thmanyah.cms_service.service;

import com.thmanyah.cms_service.dto.EpisodeDto;

public interface EpisodeService {

    Long addNewEpisode(EpisodeDto episodeDto);

    Long updateEpisode(EpisodeDto episodeDto);
}
