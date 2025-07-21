package com.thmanyah.cms_service.service;

import com.thmanyah.cms_service.dto.ProgrammeDto;

public interface ProgrammeService {

    Long addNewProgramme(ProgrammeDto programmeDto);

    Long updateProgramme(ProgrammeDto programmeDto);
}
