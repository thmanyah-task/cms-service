package com.thmanyah.cms_service.episode.repository;

import com.thmanyah.cms_service.episode.dto.EpisodeDto;
import com.thmanyah.cms_service.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode,Long> {


    @Query("""
            select new com.thmanyah.cms_service.episode.dto.EpisodeDto(ep.id,
            ep.createdDate,
            ep.updatedDate,
            ep.publishedDate,
            ep.episodeNumber,
            ep.subject,
            ep.description,
            ep.duration,
            ep.episodeUrl,
            ep.thumbnail,
            ep.programme.id)
            from Episode ep
            where ep.programme.id in :programmeIds
            """)
    List<EpisodeDto> findByProgrammeIds(@Param("programmeIds") List<Long> programmeIds);
}
