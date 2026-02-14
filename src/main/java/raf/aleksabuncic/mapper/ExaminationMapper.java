package raf.aleksabuncic.mapper;

import org.mapstruct.*;
import raf.aleksabuncic.domain.Examination;
import raf.aleksabuncic.dto.ExaminationCreateDto;
import raf.aleksabuncic.dto.ExaminationDto;
import raf.aleksabuncic.dto.ExaminationUpdateDto;

@Mapper(componentModel = "spring")
public interface ExaminationMapper {
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "veterinarian.id", target = "veterinarianId")
    ExaminationDto examinationToExaminationDto(Examination examination);

    @Mapping(source = "patientId", target = "patient.id")
    @Mapping(source = "veterinarianId", target = "veterinarian.id")
    Examination examinationCreateDtoToExamination(ExaminationCreateDto examinationCreateDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "veterinarian", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void examinationUpdateDtoToExamination(ExaminationUpdateDto examinationUpdateDto, @MappingTarget Examination examination);
}
