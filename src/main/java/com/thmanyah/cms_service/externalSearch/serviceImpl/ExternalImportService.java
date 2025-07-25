package com.thmanyah.cms_service.externalSearch.serviceImpl;

import com.thmanyah.cms_service.externalSearch.enumerations.SourceType;
import com.thmanyah.cms_service.programme.dto.ProgrammeDto;

import java.util.List;

public interface ExternalImportService {

    List<ProgrammeDto> importFromSource(SourceType sourceType, String searchQuery);
}
