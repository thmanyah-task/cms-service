package com.thmanyah.cms_service.programme.service;

import com.thmanyah.cms_service.programme.dto.ProgrammeDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;


public interface ProgrammeService {

    Long addNewProgramme(ProgrammeDto programmeDto);

    Long updateProgramme(ProgrammeDto programmeDto);

    ProgrammeDto findById(Long programmeId);

    Page<ProgrammeDto> findAllProgrammes(Integer page,Integer size);


}
