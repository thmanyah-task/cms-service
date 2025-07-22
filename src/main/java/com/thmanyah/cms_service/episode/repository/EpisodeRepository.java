package com.thmanyah.cms_service.episode.repository;

import com.thmanyah.cms_service.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode,Long> {
}
