package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientDto;

@Component
public class PatientMapper {
    public PatientDto patientToPatientDto(Patient patient) {
        PatientDto patientDto = new PatientDto();

        patientDto.setId(patient.getId());
        patientDto.setBirthDate(patient.getBirthDate());
        patientDto.setName(patient.getName());
        patientDto.setGender(patient.getGender());
        patientDto.setPassportNumber(patient.getPassportNumber());
        patientDto.setMicrochipNumber(patient.getMicrochipNumber());
        patientDto.setCartonNumber(patient.getCartonNumber());
        patientDto.setBreedId(patient.getBreed().getId());
        patientDto.setOwnerId(patient.getOwner().getId());

        return patientDto;
    }

    public Patient patientCreateDtoToPatient(PatientCreateDto patientCreateDto) {
        Patient patient = new Patient();

        patient.setBirthDate(patientCreateDto.getBirthDate());
        patient.setName(patientCreateDto.getName());
        patient.setGender(patientCreateDto.getGender());
        patient.setPassportNumber(patient.getPassportNumber());
        patient.setMicrochipNumber(patientCreateDto.getMicrochipNumber());
        patient.setCartonNumber(patientCreateDto.getCartonNumber());

        return patient;
    }
}
