package com.thmanyah.cms_service.controller;


import com.thmanyah.cms_service.dto.ApiResponse;
import com.thmanyah.cms_service.dto.EpisodeDto;
import com.thmanyah.cms_service.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/episode")
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    public ApiResponse<Long> addNewEpisode(@Valid @RequestBody EpisodeDto episodeDto){
        Long episodeId = episodeService.addNewEpisode(episodeDto);
        return ApiResponse.<Long>builder()
                .data(episodeId)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();
    }

    @PutMapping
    public ApiResponse<Long> updateEpisode(@Valid @RequestBody EpisodeDto episodeDto){
        Long episodeId = episodeService.updateEpisode(episodeDto);
        return ApiResponse.<Long>builder()
                .data(episodeId)
                .status(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now()).build();
    }
}
