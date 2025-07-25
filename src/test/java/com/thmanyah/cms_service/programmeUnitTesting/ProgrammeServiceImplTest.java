package com.thmanyah.cms_service.programmeUnitTesting;

import com.thmanyah.cms_service.episode.dto.EpisodeDto;
import com.thmanyah.cms_service.episode.repository.EpisodeRepository;
import com.thmanyah.cms_service.programme.dto.ProgrammeDto;
import com.thmanyah.cms_service.programme.entity.Category;
import com.thmanyah.cms_service.programme.entity.Language;
import com.thmanyah.cms_service.programme.entity.Programme;
import com.thmanyah.cms_service.programme.mapper.ProgrammeMapper;
import com.thmanyah.cms_service.programme.repository.CategoryRepository;
import com.thmanyah.cms_service.programme.repository.LanguageRepository;
import com.thmanyah.cms_service.programme.repository.ProgrammeRepository;
import com.thmanyah.cms_service.programme.serviceImpl.ProgrammeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProgrammeServiceImplTest {

    @Mock
    private ProgrammeRepository programmeRepository;

    @Mock
    private ProgrammeMapper programmeMapper;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EpisodeRepository episodeRepository;

    @InjectMocks
    private ProgrammeServiceImpl programmeService;

    @Test
    public void addNewProgrammes_ShouldSaveAndReturnIds_WhenValidInput() {
        Category mockCategory = new Category();
        mockCategory.setId(1);

        Language mockLanguage = new Language();
        mockLanguage.setId(2);

        ProgrammeDto dto = new ProgrammeDto();
        dto.setSubject("Subject");
        dto.setDescription("Description");
        dto.setProgrammeUrl("http://example.com");

        Category categoryDto = new Category();
        categoryDto.setId(1);
        dto.setCategory(categoryDto);

        Language languageDto = new Language();
        languageDto.setId(2);
        dto.setLanguage(languageDto);

        Programme programmeEntity = new Programme();
        programmeEntity.setCategory(mockCategory);
        programmeEntity.setLanguage(mockLanguage);

        Programme savedProgramme = new Programme();
        savedProgramme.setId(100L);

        when(categoryRepository.findById(1)).thenReturn(Optional.of(mockCategory));
        when(languageRepository.findById(2)).thenReturn(Optional.of(mockLanguage));
        when(programmeMapper.mapToProgrammeEntity(dto)).thenReturn(programmeEntity);
        when(programmeRepository.saveAll(anyList())).thenReturn(List.of(savedProgramme));

        List<Long> result = programmeService.addNewProgrammes(List.of(dto));

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0));

        verify(categoryRepository).findById(1);
        verify(languageRepository).findById(2);
        verify(programmeRepository).saveAll(anyList());
    }

    @Test
    public void updateProgramme_ShouldUpdateAndReturnId_WhenValidInput() {
        Long programmeId = 100L;

        ProgrammeDto dto = new ProgrammeDto();
        dto.setId(programmeId);
        dto.setSubject("Updated Subject");
        dto.setDescription("Updated Description");
        dto.setProgrammeUrl("http://updated.com");

        Category categoryDto = new Category();
        categoryDto.setId(1);
        dto.setCategory(categoryDto);

        Language languageDto = new Language();
        languageDto.setId(2);
        dto.setLanguage(languageDto);

        Programme existingProgramme = new Programme();
        existingProgramme.setId(programmeId);

        Category mockCategory = new Category();
        mockCategory.setId(1);

        Language mockLanguage = new Language();
        mockLanguage.setId(2);

        when(programmeRepository.findById(programmeId)).thenReturn(Optional.of(existingProgramme));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(mockCategory));
        when(languageRepository.findById(2)).thenReturn(Optional.of(mockLanguage));

        Long resultId = programmeService.updateProgramme(dto);

        assertEquals(programmeId, resultId);

        verify(programmeRepository).findById(programmeId);
        verify(categoryRepository).findById(1);
        verify(languageRepository).findById(2);
        verify(programmeMapper).updateProgramme(existingProgramme, dto);
        verify(programmeRepository).save(existingProgramme);
    }

    @Test
    public void findById_ShouldReturnProgrammeDtoWithEpisodes_WhenProgrammeExists() {
        Long programmeId = 1L;

        Programme mockProgramme = new Programme();
        mockProgramme.setId(programmeId);

        ProgrammeDto mappedDto = new ProgrammeDto();
        mappedDto.setId(programmeId);

        EpisodeDto episode1 = new EpisodeDto();
        episode1.setSubject("Episode 1");

        EpisodeDto episode2 = new EpisodeDto();
        episode2.setSubject("Episode 2");

        List<EpisodeDto> episodeDtos = List.of(episode1, episode2);

        when(programmeRepository.findById(programmeId)).thenReturn(Optional.of(mockProgramme));
        when(programmeMapper.mapToProgrammeDto(mockProgramme)).thenReturn(mappedDto);
        when(episodeRepository.findByProgrammeIds(Arrays.asList(programmeId))).thenReturn(episodeDtos);

        ProgrammeDto result = programmeService.findById(programmeId);

        assertNotNull(result);
        assertEquals(programmeId, result.getId());
        assertEquals(2, result.getEpisodeDtoList().size());
        assertEquals("Episode 1", result.getEpisodeDtoList().get(0).getSubject());

        verify(programmeRepository).findById(programmeId);
        verify(programmeMapper).mapToProgrammeDto(mockProgramme);
        verify(episodeRepository).findByProgrammeIds(Arrays.asList(programmeId));
    }

    @Test
    public void findAllProgrammes_ShouldReturnPagedResult_WhenProgrammesExist() {
        Programme programme = new Programme();
        programme.setId(1L);

        ProgrammeDto programmeDto = new ProgrammeDto();
        programmeDto.setId(1L);

        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setSubject("Episode 1");
        episodeDto.setProgrammeId(1L);

        Page<Programme> mockPage = new PageImpl<>(List.of(programme));

        when(programmeRepository.filterAndFindProgrammes(
                anyString(), anyString(), any(), anyString(), anyString(),
                anyString(), anyString(), any(), any(),any(), any(Pageable.class)
        )).thenReturn(mockPage);

        when(programmeMapper.mapToProgrammeDto(programme)).thenReturn(programmeDto);
        when(episodeRepository.findByProgrammeIds(Arrays.asList(1L))).thenReturn(List.of(episodeDto)); // ✅ correct method

        Page<ProgrammeDto> result = programmeService.findAllProgrammes(
                "subject", "description", LocalDate.now(),
                "category", "language", "epSubject",
                "epDescription", 1, LocalDate.now(), 2.0,1, 10
        );

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        ProgrammeDto resultDto = result.getContent().get(0);
        assertEquals(1L, resultDto.getId());
        assertEquals(1, resultDto.getEpisodeDtoList().size());
        assertEquals("Episode 1", resultDto.getEpisodeDtoList().get(0).getSubject());

        verify(programmeRepository).filterAndFindProgrammes(
                anyString(), anyString(), any(), anyString(), anyString(),
                anyString(), anyString(), any(), any(),any(), any(Pageable.class)
        );
        verify(programmeMapper).mapToProgrammeDto(programme);
        verify(episodeRepository).findByProgrammeIds(Arrays.asList(1L));
    }




}
