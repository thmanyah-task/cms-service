package com.thmanyah.cms_service.externalSearch.factory;

import com.thmanyah.cms_service.externalSearch.dataSourcesTypes.YoutubeDataSource;
import com.thmanyah.cms_service.externalSearch.enumerations.SourceType;
import com.thmanyah.cms_service.externalSearch.shared.ExternalDataSource;
import org.springframework.stereotype.Component;

import static com.thmanyah.cms_service.externalSearch.enumerations.SourceType.YOUTUBE;

@Component
public class ExternalDataSourceFactory {

    private final YoutubeDataSource youTubeDataSource;

    public ExternalDataSourceFactory(YoutubeDataSource youTubeDataSource) {
        this.youTubeDataSource = youTubeDataSource;
    }

    public ExternalDataSource getDataSource(SourceType sourceType) {
        return switch (sourceType) {
            case YOUTUBE -> youTubeDataSource;
            default -> throw new IllegalArgumentException("Unsupported source: " + sourceType);
        };
    }
}
