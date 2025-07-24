package com.thmanyah.cms_service.episodeUnitTesting;

import com.thmanyah.cms_service.episode.dto.EpisodeDto;
import com.thmanyah.cms_service.episode.entity.Episode;
import com.thmanyah.cms_service.episode.mapper.EpisodeMapper;
import com.thmanyah.cms_service.episode.repository.EpisodeRepository;
import com.thmanyah.cms_service.programme.entity.Programme;
import com.thmanyah.cms_service.programme.repository.ProgrammeRepository;
import com.thmanyah.cms_service.shared.exception.ValidationException;
import com.thmanyah.cms_service.episode.serviceImpl.EpisodeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EpisodeServiceImplTest {

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private EpisodeMapper episodeMapper;

    @Mock
    private ProgrammeRepository programmeRepository;

    @InjectMocks
    private EpisodeServiceImpl episodeService;

    @Test
    public void addNewEpisodes_ShouldSaveAndReturnIds_WhenValidInput() {
        Programme programme = new Programme();
        programme.setId(1L);

        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setSubject("Episode 1");
        episodeDto.setDescription("Description");
        episodeDto.setEpisodeUrl("http://episode.com");
        episodeDto.setDuration(30.0);
        episodeDto.setEpisodeNumber(1);
        episodeDto.setPublishedDate(LocalDate.now());
        episodeDto.setProgramme(programme);

        Episode mappedEpisode = new Episode();
        mappedEpisode.setId(100L);
        mappedEpisode.setProgramme(programme);

        when(programmeRepository.findById(1L)).thenReturn(Optional.of(programme));
        when(episodeMapper.mapToEpisodeEntity(episodeDto)).thenReturn(mappedEpisode);
        when(episodeRepository.save(any(Episode.class))).thenReturn(mappedEpisode);

        List<Long> result = episodeService.addNewEpisodes(List.of(episodeDto));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0));

        verify(programmeRepository).findById(1L);
        verify(episodeMapper).mapToEpisodeEntity(episodeDto);
        verify(episodeRepository).save(any(Episode.class));
    }

    @Test
    public void updateEpisode_ShouldUpdateAndReturnId_WhenValidInput() {
        Long episodeId = 100L;

        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setId(episodeId);

        Programme programmeDto = new Programme();
        programmeDto.setId(1L);
        episodeDto.setProgramme(programmeDto);

        Episode existingEpisode = new Episode();
        existingEpisode.setId(episodeId);

        Programme existingProgramme = new Programme();
        existingProgramme.setId(1L);

        when(episodeRepository.findById(episodeId)).thenReturn(Optional.of(existingEpisode));
        when(programmeRepository.findById(1L)).thenReturn(Optional.of(existingProgramme));

        Long resultId = episodeService.updateEpisode(episodeDto);

        assertEquals(episodeId, resultId);

        verify(episodeRepository).findById(episodeId);
        verify(programmeRepository).findById(1L);
        verify(episodeMapper).updateEpisode(existingEpisode, episodeDto);
        verify(episodeRepository).save(existingEpisode);
    }

    @Test
    public void findEpisodeById_ShouldReturnEpisodeDto_WhenEpisodeExists() {
        Long episodeId = 1L;
        Episode episode = new Episode();
        episode.setId(episodeId);

        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setId(episodeId);

        when(episodeRepository.findById(episodeId)).thenReturn(Optional.of(episode));
        when(episodeMapper.mapToEpisodeDto(episode)).thenReturn(episodeDto);

        EpisodeDto result = episodeService.findEpisodeById(episodeId);

        assertNotNull(result);
        assertEquals(episodeId, result.getId());

        verify(episodeRepository).findById(episodeId);
        verify(episodeMapper).mapToEpisodeDto(episode);
    }



}
