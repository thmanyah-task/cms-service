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
    public Long addNewProgramme(ProgrammeDto programmeDto) {
        if (programmeDto.getDescription() == null || programmeDto.getDescription().isEmpty()){
            throw new ValidationException("Description Cannot Be Null");
        }
        if (programmeDto.getSubject() == null || programmeDto.getSubject().isEmpty()){
            throw new ValidationException("Subject Cannot Be Null");
        }
        if (programmeDto.getProgrammeUrl() == null || programmeDto.getProgrammeUrl().isEmpty()){
            throw new ValidationException("ProgrammeUrl Cannot Be Null");
        }
        Category category = categoryRepository.findById(programmeDto.getCategory().getId()).orElse(null);
        if (category == null){
            throw new ValidationException("No Category Found With The Provided Id");
        }
        Language language = languageRepository.findById(programmeDto.getLanguage().getId()).orElse(null);
        if (language == null){
            throw new ValidationException("No Language Found With The Provided Id");
        }
        Programme programme = programmeMapper.mapToProgrammeEntity(programmeDto);
        programme.setCreatedDate(LocalDateTime.now());
        programmeRepository.save(programme);
        return programme.getId();
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
            String categoryNameAr,
            String languageNameAr,
            String episodeSubject,
            String episodeDescription,
            Integer episodeNumber,
            LocalDate publishedDate,
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
                categoryNameAr,
                languageNameAr,
                episodeSubject,
                episodeDescription,
                episodeNumber,
                publishedDate,
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
