package raf.aleksabuncic.service;

import org.springframework.data.domain.Pageable;
import raf.aleksabuncic.dto.PatientDto;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientSearchDto;
import raf.aleksabuncic.dto.PatientUpdateDto;

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
     * Find all patients with pagination
     *
     * @param paginationDto Pagination object
     * @return Slice of PatientDto
     */
    Iterable<PatientDto> findAllPatients(PatientSearchDto patientSearchDto, Pageable pageable);

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
