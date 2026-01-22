package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientUpdateDto;

import java.util.List;

public interface PatientService {
    /**
     * \
     * Find patient by id
     *
     * @param id Patient id
     * @return PatientDto
     */
    PatientDto findPatientById(Long id);

    /**
     * Find all patients
     *
     * @return List of PatientDto
     */
    List<PatientDto> findAllPatients();

    /**
     * Create new patient
     *
     * @param patientCreateDto Patient create object
     * @return PatientDto
     */
    PatientDto createPatient(PatientCreateDto patientCreateDto);

    /**
     * Update patient
     *
     * @param id               Patient id
     * @param patientUpdateDto Patient update object
     * @return PatientDto
     */
    PatientDto updatePatient(Long id, PatientUpdateDto patientUpdateDto);

    /**
     * Delete patient by id
     *
     * @param id Patient id
     */
    void deletePatient(Long id);
}
