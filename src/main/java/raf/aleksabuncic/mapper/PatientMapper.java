package raf.aleksabuncic.mapper;

import org.mapstruct.*;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientUpdateDto;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "breed.id", target = "breedId")
    PatientDto patientToPatientDto(Patient patient);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(source = "breedId", target = "breed.id")
    Patient patientCreateDtoToPatient(PatientCreateDto patientCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "breed", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patientUpdateDtoToPatient(PatientUpdateDto patientUpdateDto, @MappingTarget Patient patient);
}
