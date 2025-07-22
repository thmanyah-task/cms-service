package com.thmanyah.cms_service.controller;


import com.thmanyah.cms_service.dto.ApiResponse;
import com.thmanyah.cms_service.dto.ProgrammeDto;
import com.thmanyah.cms_service.service.ProgrammeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/programme")
@RequiredArgsConstructor
public class ProgrammeController {

    private final ProgrammeService programmeService;

    @PostMapping
    @PreAuthorize("hasRole('CONTENT_EDITOR') or hasRole('CONTENT_MANAGER')")
    public ApiResponse<Long> addNewProgramme(@Valid@RequestBody ProgrammeDto programmeDto){
        Long programmeId = programmeService.addNewProgramme(programmeDto);
        return ApiResponse.<Long>builder()
                .data(programmeId)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();
    }

    @PutMapping
    @PreAuthorize("hasRole('CONTENT_EDITOR') or hasRole('CONTENT_MANAGER')")
    public ApiResponse<Long> updateProgramme(@Valid@RequestBody ProgrammeDto programmeDto){
        Long programmeId = programmeService.updateProgramme(programmeDto);
        return ApiResponse.<Long>builder()
                .data(programmeId)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();
    }
}
