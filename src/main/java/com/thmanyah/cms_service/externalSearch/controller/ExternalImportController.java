package com.thmanyah.cms_service.externalSearch.controller;


import com.thmanyah.cms_service.externalSearch.enumerations.SourceType;
import com.thmanyah.cms_service.externalSearch.service.ExternalImportService;
import com.thmanyah.cms_service.programme.dto.ProgrammeDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/external-search")
public class ExternalImportController {

    private final ExternalImportService importService;

    public ExternalImportController(ExternalImportService importService) {
        this.importService = importService;
    }


    @GetMapping("/import")
    @PreAuthorize("hasRole('CONTENT_EDITOR') or hasRole('CONTENT_MANAGER')")
    @SecurityRequirement(name = "bearerAuth")
    public com.thmanyah.cms_service.shared.exception.dto.ApiResponse<List<ProgrammeDto>> importData(
            @RequestParam SourceType sourceType,
            @RequestParam String searchQuery
    ) {
        List<ProgrammeDto> result = importService.importFromSource(sourceType, searchQuery);
        return com.thmanyah.cms_service.shared.exception.dto.ApiResponse.<List<ProgrammeDto>>builder()
                .data(result)
                .totalCount(result.stream().count())
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
