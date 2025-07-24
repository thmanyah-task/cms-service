package com.thmanyah.cms_service.episode.controller;


import com.thmanyah.cms_service.shared.exception.dto.ApiResponse;
import com.thmanyah.cms_service.episode.service.EpisodeService;
import com.thmanyah.cms_service.episode.dto.EpisodeDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/episode")
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    @PreAuthorize("hasRole('CONTENT_EDITOR') or hasRole('CONTENT_MANAGER')")
    public ApiResponse<List<Long>> addNewEpisode(@Valid @RequestBody List<EpisodeDto> episodeDtos){
        List<Long> episodeIds = episodeService.addNewEpisodes(episodeDtos);
        return ApiResponse.<List<Long>>builder()
                .data(episodeIds)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();
    }

    @PutMapping
    @PreAuthorize("hasRole('CONTENT_EDITOR') or hasRole('CONTENT_MANAGER')")
    public ApiResponse<Long> updateEpisode(@Valid @RequestBody EpisodeDto episodeDto){
        Long episodeId = episodeService.updateEpisode(episodeDto);
        return ApiResponse.<Long>builder()
                .data(episodeId)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();
    }

    @GetMapping("/{episodeId}")
    public ApiResponse<EpisodeDto> getEpisodeById(@PathVariable("episodeId") Long episodeId){
        EpisodeDto episodeDto = episodeService.findEpisodeById(episodeId);
        return ApiResponse.<EpisodeDto>builder()
                .data(episodeDto)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();
    }
}
