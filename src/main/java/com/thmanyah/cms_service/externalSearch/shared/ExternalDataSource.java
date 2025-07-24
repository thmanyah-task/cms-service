package com.thmanyah.cms_service.externalSearch.shared;

import com.thmanyah.cms_service.programme.dto.ProgrammeDto;

import java.util.List;

public interface ExternalDataSource {

    List<ProgrammeDto> fetchData(String sourceId);
}
