package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientUpdateDto;

public interface PatientService {
    PatientDto findPatientById(Long id);

    PatientDto createPatient(PatientCreateDto patientCreateDto);

    PatientDto updatePatient(PatientUpdateDto patientUpdateDto);

    void deletePatient(Long id);
}
