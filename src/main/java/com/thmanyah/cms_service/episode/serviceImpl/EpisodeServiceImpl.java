package com.thmanyah.cms_service.episode.serviceImpl;

import com.thmanyah.cms_service.episode.dto.EpisodeDto;
import com.thmanyah.cms_service.episode.entity.Episode;
import com.thmanyah.cms_service.episode.mapper.EpisodeMapper;
import com.thmanyah.cms_service.episode.repository.EpisodeRepository;
import com.thmanyah.cms_service.episode.service.EpisodeService;
import com.thmanyah.cms_service.programme.entity.Programme;
import com.thmanyah.cms_service.shared.exception.ValidationException;
import com.thmanyah.cms_service.programme.repository.ProgrammeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final EpisodeMapper episodeMapper;
    private final ProgrammeRepository programmeRepository;


    @Override
    public List<Long> addNewEpisodes(List<EpisodeDto> episodeDtoList) {
        if (episodeDtoList == null || episodeDtoList.isEmpty()) {
            throw new ValidationException("Episode list cannot be empty");
        }
        List<Long> episodeIds = new ArrayList<>();
        for (EpisodeDto episodeDto : episodeDtoList) {
            Programme programme = programmeRepository.findById(episodeDto.getProgramme().getId())
                    .orElseThrow(() -> new ValidationException("Programme not found with ID: " + episodeDto.getProgramme().getId()));

            if (episodeDto.getDescription() == null || episodeDto.getDescription().isEmpty()) {
                throw new ValidationException("Description Cannot Be Null");
            }
            if (episodeDto.getSubject() == null || episodeDto.getSubject().isEmpty()) {
                throw new ValidationException("Subject Cannot Be Null");
            }
            if (episodeDto.getEpisodeUrl() == null || episodeDto.getEpisodeUrl().isEmpty()) {
                throw new ValidationException("EpisodeUrl Cannot Be Null");
            }
            if (episodeDto.getDuration() == null) {
                throw new ValidationException("Duration Cannot Be Null");
            }
            if (episodeDto.getEpisodeNumber() == null) {
                throw new ValidationException("EpisodeNumber Cannot Be Null");
            }
            if (episodeDto.getPublishedDate() == null) {
                throw new ValidationException("Published Date Cannot Be Null");
            }
            Episode episode = episodeMapper.mapToEpisodeEntity(episodeDto);
            episode.setCreatedDate(LocalDateTime.now());
            episode.setProgramme(programme);
            episodeRepository.save(episode);
            episodeIds.add(episode.getId());
        }

        return episodeIds;
    }


    @Override
    public Long updateEpisode(EpisodeDto episodeDto) {
        Episode episode = episodeRepository.findById(episodeDto.getId()).orElse(null);
        if (episode == null){
            throw new ValidationException("Episode With Provide Id Cannot Be Null");
        }
        if (episodeDto.getProgramme() != null){
            Programme programme = programmeRepository.findById(episodeDto.getProgramme().getId()).orElse(null);
            if (programme == null){
                throw new ValidationException("Programme Cannot Be Found With The Provided ProgrammeId");
            }
        }
        episodeMapper.updateEpisode(episode,episodeDto);
        episode.setUpdatedDate(LocalDateTime.now());
        episodeRepository.save(episode);
        return episode.getId();
    }

    @Override
    public EpisodeDto findEpisodeById(Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId).orElse(null);
        if (episode == null){
            throw new ValidationException("Episode Not Existed With The Given Provided Id");
        }
        EpisodeDto episodeDto = episodeMapper.mapToEpisodeDto(episode);
        return episodeDto;
    }
}
