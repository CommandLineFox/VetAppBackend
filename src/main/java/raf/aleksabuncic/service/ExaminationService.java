package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.ExaminationDto;
import raf.aleksabuncic.dto.ExaminationCreateDto;
import raf.aleksabuncic.dto.ExaminationUpdateDto;

import java.util.List;

public interface ExaminationService {
    /**
     * Find examination by id
     *
     * @param id Examination id
     * @return ExaminationDto
     */
    ExaminationDto findExaminationById(Long id);

    /**
     * Find all examinations
     *
     * @return List of ExaminationDto
     */
    List<ExaminationDto> findAllExaminations();

    /**
     * Create new examination
     *
     * @param examinationCreateDto Examination create object
     * @return ExaminationDto
     */
    ExaminationDto createExamination(ExaminationCreateDto examinationCreateDto);

    /**
     * Update examination
     *
     * @param id                   Examination id
     * @param examinationUpdateDto Examination update object
     * @return ExaminationDto
     */
    ExaminationDto updateExamination(Long id, ExaminationUpdateDto examinationUpdateDto);

    /**
     * Delete examination by id
     *
     * @param id Examination id
     */
    void deleteExamination(Long id);
}
