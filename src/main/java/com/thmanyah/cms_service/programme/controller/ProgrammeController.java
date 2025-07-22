package com.thmanyah.cms_service.programme.controller;


import com.thmanyah.cms_service.shared.exception.dto.ApiResponse;
import com.thmanyah.cms_service.programme.dto.ProgrammeDto;
import com.thmanyah.cms_service.programme.service.ProgrammeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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


    @GetMapping("/{programmeId}")
    public ApiResponse<ProgrammeDto> getProgramme(@PathVariable("programmeId") Long programmeId){
        ProgrammeDto programmeDto = programmeService.findById(programmeId);
        return ApiResponse.<ProgrammeDto>builder()
                .data(programmeDto)
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now()).build();
    }


    @GetMapping("/list")
    public ApiResponse<List<ProgrammeDto>> getProgramme(@RequestParam(name = "page",required = true,defaultValue = "0") Integer page,
                                                        @RequestParam(name = "size",required = true,defaultValue = "10") Integer size) {
        Page<ProgrammeDto> programmeDtos = programmeService.findAllProgrammes(page, size);
        return ApiResponse.<List<ProgrammeDto>>builder()
                .data(programmeDtos.getContent())
                .totalPages(programmeDtos.getTotalPages())
                .totalCount(programmeDtos.getTotalElements())
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
