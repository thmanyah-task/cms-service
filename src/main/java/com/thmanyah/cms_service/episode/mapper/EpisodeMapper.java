package com.thmanyah.cms_service.episode.mapper;

import com.thmanyah.cms_service.episode.dto.EpisodeDto;
import com.thmanyah.cms_service.episode.entity.Episode;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EpisodeMapper {

    Episode mapToEpisodeEntity(EpisodeDto episodeDto);

    EpisodeDto mapToEpisodeDto(Episode episode);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEpisode(@MappingTarget Episode episode, EpisodeDto episodeDto);

}
