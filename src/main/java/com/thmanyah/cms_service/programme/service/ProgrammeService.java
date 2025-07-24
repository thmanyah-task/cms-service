package com.thmanyah.cms_service.programme.service;

import com.thmanyah.cms_service.programme.dto.ProgrammeDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;


public interface ProgrammeService {

    Long addNewProgramme(ProgrammeDto programmeDto);

    Long updateProgramme(ProgrammeDto programmeDto);

    ProgrammeDto findById(Long programmeId);

    Page<ProgrammeDto> findAllProgrammes(
            String programmeSubject,
            String programmeDescription,
            LocalDate programmePublishedDate,
            String categoryNameAr,
            String languageNameAr,
            String episodeSubject,
            String episodeDescription,
            Integer episodeNumber,
            LocalDate episodePublishedDate,
            Integer page,
            Integer size
    );


}
