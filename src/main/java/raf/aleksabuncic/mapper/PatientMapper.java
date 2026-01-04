package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Patient;
import raf.aleksabuncic.dto.PatientRequestDto;
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

    public Patient patientRequestDtoToPatient(PatientRequestDto patientRequestDto) {
        Patient patient = new Patient();

        patient.setBirthDate(patientRequestDto.getBirthDate());
        patient.setName(patientRequestDto.getName());
        patient.setGender(patientRequestDto.getGender());
        patient.setPassportNumber(patient.getPassportNumber());
        patient.setMicrochipNumber(patientRequestDto.getMicrochipNumber());
        patient.setCartonNumber(patientRequestDto.getCartonNumber());

        return patient;
    }
}
