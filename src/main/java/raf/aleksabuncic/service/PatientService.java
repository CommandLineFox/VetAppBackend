package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientRequestDto;

public interface PatientService {
    PatientDto findPatientById(Long id);

    PatientDto createPatient(PatientRequestDto patientRequestDto);

    PatientDto updatePatient(PatientRequestDto patientRequestDto);

    void deletePatient(Long id);
}
