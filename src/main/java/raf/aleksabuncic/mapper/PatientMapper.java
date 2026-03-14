package raf.aleksabuncic.mapper;

import org.mapstruct.*;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientDisplayDto;
import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientUpdateDto;

@Mapper(componentModel = "spring", uses = {OwnerMapper.class, BreedMapper.class})
public interface PatientMapper {
    PatientDto patientToPatientDto(Patient patient);

    PatientDisplayDto patientToPatientDisplayDto(Patient patient);

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
