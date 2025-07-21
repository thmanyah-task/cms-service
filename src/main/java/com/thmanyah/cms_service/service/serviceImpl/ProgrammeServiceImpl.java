package com.thmanyah.cms_service.service.serviceImpl;

import com.thmanyah.cms_service.dto.ProgrammeDto;
import com.thmanyah.cms_service.entity.Category;
import com.thmanyah.cms_service.entity.Language;
import com.thmanyah.cms_service.entity.Programme;
import com.thmanyah.cms_service.exception.ValidationException;
import com.thmanyah.cms_service.mapper.ProgrammeMapper;
import com.thmanyah.cms_service.repository.CategoryRepository;
import com.thmanyah.cms_service.repository.LanguageRepository;
import com.thmanyah.cms_service.repository.ProgrammeRepository;
import com.thmanyah.cms_service.service.ProgrammeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgrammeServiceImpl implements ProgrammeService {

    private final ProgrammeRepository programmeRepository;
    private final ProgrammeMapper programmeMapper;
    private final LanguageRepository languageRepository;
    private final CategoryRepository categoryRepository;

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
}
