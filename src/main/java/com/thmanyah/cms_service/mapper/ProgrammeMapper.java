package com.thmanyah.cms_service.mapper;

import com.thmanyah.cms_service.dto.ProgrammeDto;
import com.thmanyah.cms_service.entity.Programme;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProgrammeMapper {

    ProgrammeDto mapToProgrammeDto(Programme programme);

    Programme mapToProgrammeEntity(ProgrammeDto programmeDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProgramme(@MappingTarget Programme programme, ProgrammeDto programmeDto);

}
