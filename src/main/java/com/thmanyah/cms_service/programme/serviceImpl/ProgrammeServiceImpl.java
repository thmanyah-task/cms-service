package com.thmanyah.cms_service.programme.serviceImpl;

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
import com.thmanyah.cms_service.programme.service.ProgrammeService;
import com.thmanyah.cms_service.shared.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgrammeServiceImpl implements ProgrammeService {

    private final ProgrammeRepository programmeRepository;
    private final ProgrammeMapper programmeMapper;
    private final LanguageRepository languageRepository;
    private final CategoryRepository categoryRepository;
    private final EpisodeRepository episodeRepository;

    @Override
    public List<Long> addNewProgrammes(List<ProgrammeDto> programmeDtoList) {
        if (programmeDtoList == null || programmeDtoList.isEmpty()) {
            throw new ValidationException("Programme list cannot be null or empty");
        }
        List<Long> programmeIds = new ArrayList<>();

        List<Programme> programmesToSave = new ArrayList<>();

        for (ProgrammeDto dto : programmeDtoList) {
            if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
                throw new ValidationException("Description cannot be null");
            }
            if (dto.getSubject() == null || dto.getSubject().isEmpty()) {
                throw new ValidationException("Subject cannot be null");
            }
            if (dto.getProgrammeUrl() == null || dto.getProgrammeUrl().isEmpty()) {
                throw new ValidationException("ProgrammeUrl cannot be null");
            }
            Category category = categoryRepository.findById(dto.getCategory().getId())
                    .orElseThrow(() -> new ValidationException("No Category Found With The Provided Id"));
            Language language = languageRepository.findById(dto.getLanguage().getId())
                    .orElseThrow(() -> new ValidationException("No Language Found With The Provided Id"));

            Programme programme = programmeMapper.mapToProgrammeEntity(dto);
            programme.setCategory(category);
            programme.setLanguage(language);
            programme.setCreatedDate(LocalDateTime.now());
            programmesToSave.add(programme);
        }

        List<Programme> savedProgramme = programmeRepository.saveAll(programmesToSave);
        for (Programme p : savedProgramme) {
            programmeIds.add(p.getId());
        }
        return programmeIds;
    }


    @Override
    public Long updateProgramme(ProgrammeDto programmeDto) {
        Programme programme = programmeRepository.findById(programmeDto.getId()).orElse(null);
        if (programme == null){
            throw new ValidationException("Programme Not Found With Given Id");
        }
        if (programmeDto.getCategory() != null){
            Category category = categoryRepository.findById(programmeDto.getCategory().getId()).orElse(null);
            if (category == null){
                throw new ValidationException("No Category Found With The Provided Id");
            }
        }
        if (programmeDto.getLanguage() != null){
            Language language = languageRepository.findById(programmeDto.getLanguage().getId()).orElse(null);
            if (language == null){
                throw new ValidationException("No Language Found With The Provided Id");
            }
        }
        programmeMapper.updateProgramme(programme,programmeDto);
        programme.setUpdatedDate(LocalDateTime.now());
        programmeRepository.save(programme);
        return programme.getId();
    }

    @Override
    public ProgrammeDto findById(Long programmeId) {
        Programme programme = programmeRepository.findById(programmeId).orElse(null);
        if (programme == null){
            throw new ValidationException("Provided Programme Id Is Not Existed");
        }
        ProgrammeDto programmeDto = programmeMapper.mapToProgrammeDto(programme);
        List<EpisodeDto> episodeList = episodeRepository.findByProgrammeId(programmeId);
        programmeDto.setEpisodeDtoList(!episodeList.isEmpty() ? episodeList : new ArrayList<>());
        return programmeDto;
    }

    @Override
    public Page<ProgrammeDto> findAllProgrammes(
            String programmeSubject,
            String programmeDescription,
            LocalDate programmePublishedDate,
            String categoryNameAr,
            String languageNameAr,
            String episodeSubject,
            String episodeDescription,
            Integer episodeNumber,
            LocalDate episodePublishedDate,
            Integer page,
            Integer size) {
        Pageable pageable;
        if (page != null && size != null && page > 0) {
            pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        } else {
            pageable = Pageable.unpaged();
        }

        Page<Programme> programmePage = programmeRepository.filterAndFindProgrammes(
                programmeSubject,
                programmeDescription,
                programmePublishedDate,
                categoryNameAr,
                languageNameAr,
                episodeSubject,
                episodeDescription,
                episodeNumber,
                episodePublishedDate,
                pageable
        );

        if (programmePage.isEmpty()) {
            return Page.empty();
        }

        Page<ProgrammeDto> programmeDtos = programmePage.map(programmeMapper::mapToProgrammeDto);

        programmeDtos.getContent().forEach(programmeDto -> {
            List<EpisodeDto> episodeDtoList = episodeRepository.findByProgrammeId(programmeDto.getId());
            programmeDto.setEpisodeDtoList(episodeDtoList);
        });

        return programmeDtos;
    }

}
