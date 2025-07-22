package com.thmanyah.cms_service.programme.serviceImpl;

import com.thmanyah.cms_service.programme.dto.ProgrammeDto;
import com.thmanyah.cms_service.programme.entity.Category;
import com.thmanyah.cms_service.programme.entity.Language;
import com.thmanyah.cms_service.programme.entity.Programme;
import com.thmanyah.cms_service.shared.exception.ValidationException;
import com.thmanyah.cms_service.programme.mapper.ProgrammeMapper;
import com.thmanyah.cms_service.programme.service.ProgrammeService;
import com.thmanyah.cms_service.programme.repository.CategoryRepository;
import com.thmanyah.cms_service.programme.repository.LanguageRepository;
import com.thmanyah.cms_service.programme.repository.ProgrammeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
