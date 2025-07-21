package com.thmanyah.cms_service.mapper;

import com.thmanyah.cms_service.dto.EpisodeDto;
import com.thmanyah.cms_service.dto.ProgrammeDto;
import com.thmanyah.cms_service.entity.Episode;
import com.thmanyah.cms_service.entity.Programme;
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
