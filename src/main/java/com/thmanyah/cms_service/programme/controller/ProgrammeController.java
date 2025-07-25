package com.thmanyah.cms_service.programme.controller;


import com.thmanyah.cms_service.programme.dto.ProgrammeDto;
import com.thmanyah.cms_service.programme.service.ProgrammeService;
import com.thmanyah.cms_service.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/programme")
@RequiredArgsConstructor
public class ProgrammeController {

    private final ProgrammeService programmeService;

    @PostMapping
    @PreAuthorize("hasRole('CONTENT_EDITOR') or hasRole('CONTENT_MANAGER')")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<List<Long>> addNewProgramme(@Valid@RequestBody List<ProgrammeDto> programmeDtos){
        List<Long> programmeIds = programmeService.addNewProgrammes(programmeDtos);
        return ApiResponse.<List<Long>>builder()
                .data(programmeIds)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();
    }

    @PutMapping
    @PreAuthorize("hasRole('CONTENT_EDITOR') or hasRole('CONTENT_MANAGER')")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<Long> updateProgramme(@Valid@RequestBody ProgrammeDto programmeDto){
        Long programmeId = programmeService.updateProgramme(programmeDto);
        return ApiResponse.<Long>builder()
                .data(programmeId)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();
    }


    @GetMapping("/{programmeId}")
    public ApiResponse<ProgrammeDto> getProgramme(@PathVariable("programmeId") Long programmeId){
        ProgrammeDto programmeDto = programmeService.findById(programmeId);
        return ApiResponse.<ProgrammeDto>builder()
                .data(programmeDto)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now()).build();
    }



    @GetMapping("/list")
    public ApiResponse<List<ProgrammeDto>> getListOfProgrammes(
            @RequestParam(name = "programmeSubject", required = false) String programmeSubject,
            @RequestParam(name = "programmeDescription", required = false) String programmeDescription,
            @RequestParam(name = "programmePublishedDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate programmePublishedDate,
            @RequestParam(name = "categoryNameAr", required = false) String categoryNameAr,
            @RequestParam(name = "languageNameAr", required = false) String languageNameAr,
            @RequestParam(name = "episodeSubject", required = false) String episodeSubject,
            @RequestParam(name = "episodeDescription", required = false) String episodeDescription,
            @RequestParam(name = "episodeNumber", required = false) Integer episodeNumber,
            @RequestParam(name = "episodePublishedDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate episodePublishedDate,
            @RequestParam(name = "page", required = true) Integer page,
            @RequestParam(name = "size", required = true) Integer size
    ) {
        Page<ProgrammeDto> programmeDtos = programmeService.findAllProgrammes(
                programmeSubject,
                programmeDescription,
                programmePublishedDate,
                categoryNameAr,
                languageNameAr,
                episodeSubject,
                episodeDescription,
                episodeNumber,
                episodePublishedDate,
                page,
                size
        );

        return ApiResponse.<List<ProgrammeDto>>builder()
                .data(programmeDtos.getContent())
                .totalPages(programmeDtos.getTotalPages())
                .totalCount(programmeDtos.getTotalElements())
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
    }


}
