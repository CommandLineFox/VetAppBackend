package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientUpdateDto;

import java.util.List;

public interface PatientService {
    PatientDto findPatientById(Long id);

    List<PatientDto> findAllPatients();

    PatientDto createPatient(PatientCreateDto patientCreateDto);

    PatientDto updatePatient(Long id, PatientUpdateDto patientUpdateDto);

    void deletePatient(Long id);
}
