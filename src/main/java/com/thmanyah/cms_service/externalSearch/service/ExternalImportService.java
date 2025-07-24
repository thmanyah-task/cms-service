package com.thmanyah.cms_service.externalSearch.service;

import com.thmanyah.cms_service.externalSearch.enumerations.SourceType;
import com.thmanyah.cms_service.externalSearch.factory.ExternalDataSourceFactory;
import com.thmanyah.cms_service.externalSearch.shared.ExternalDataSource;
import com.thmanyah.cms_service.programme.dto.ProgrammeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalImportService {
    private final ExternalDataSourceFactory factory;

    public ExternalImportService(ExternalDataSourceFactory factory) {
        this.factory = factory;
    }

    public List<ProgrammeDto> importFromSource(SourceType sourceType, String searchQuery) {
        ExternalDataSource dataSource = factory.getDataSource(sourceType);
        return dataSource.fetchData(searchQuery);
    }
}
