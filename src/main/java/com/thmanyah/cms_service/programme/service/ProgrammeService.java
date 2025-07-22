package com.thmanyah.cms_service.programme.service;

import com.thmanyah.cms_service.programme.dto.ProgrammeDto;

public interface ProgrammeService {

    Long addNewProgramme(ProgrammeDto programmeDto);

    Long updateProgramme(ProgrammeDto programmeDto);
}
