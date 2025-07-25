package com.thmanyah.cms_service.programme.repository;


import com.thmanyah.cms_service.programme.entity.Programme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ProgrammeRepository extends JpaRepository<Programme,Long> {



        @Query("""
        SELECT DISTINCT p FROM Programme p
        LEFT JOIN p.category c
        LEFT JOIN p.language l
        LEFT JOIN Episode e ON e.programme = p
        WHERE (:programmeSubject IS NULL OR :programmeSubject = '' OR LOWER(p.subject) LIKE LOWER(CONCAT('%', :programmeSubject, '%')))
        AND (:programmeDescription IS NULL OR :programmeDescription = '' OR LOWER(p.description) LIKE LOWER(CONCAT('%', :programmeDescription, '%')))
        AND (CAST(:programmePublishedDate AS date) IS NULL OR p.publishedDate = CAST(:programmePublishedDate AS date))
        AND (:categoryNameAr IS NULL OR :categoryNameAr = '' OR LOWER(c.nameAr) LIKE LOWER(CONCAT('%', :categoryNameAr, '%')))
        AND (:languageNameAr IS NULL OR :languageNameAr = '' OR LOWER(l.nameAr) LIKE LOWER(CONCAT('%', :languageNameAr, '%')))
        AND (:episodeSubject IS NULL OR :episodeSubject = '' OR LOWER(e.subject) LIKE LOWER(CONCAT('%', :episodeSubject, '%')))
        AND (:episodeDescription IS NULL OR :episodeDescription = '' OR LOWER(e.description) LIKE LOWER(CONCAT('%', :episodeDescription, '%')))
        AND (:episodeNumber IS NULL OR e.episodeNumber = :episodeNumber)
        AND (CAST(:episodePublishedDate AS date) IS NULL OR e.publishedDate = CAST(:episodePublishedDate AS date))
        AND (:episodeDuration IS NULL OR e.duration = :episodeDuration)
        """)
        Page<Programme> filterAndFindProgrammes(
                @Param("programmeSubject") String programmeSubject,
                @Param("programmeDescription") String programmeDescription,
                @Param("programmePublishedDate") LocalDate programmePublishedDate,
                @Param("categoryNameAr") String categoryNameAr,
                @Param("languageNameAr") String languageNameAr,
                @Param("episodeSubject") String episodeSubject,
                @Param("episodeDescription") String episodeDescription,
                @Param("episodeNumber") Integer episodeNumber,
                @Param("episodePublishedDate") LocalDate episodePublishedDate,
                @Param("episodeDuration") Double episodeDuration,
                Pageable pageable
        );


}


