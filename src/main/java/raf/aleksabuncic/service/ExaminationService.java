package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.ExaminationDto;
import raf.aleksabuncic.dto.ExaminationCreateDto;
import raf.aleksabuncic.dto.ExaminationUpdateDto;

import java.util.List;

public interface ExaminationService {
    ExaminationDto findExaminationById(Long id);

    List<ExaminationDto> findAllExaminations();

    ExaminationDto createExamination(ExaminationCreateDto examinationCreateDto);

    ExaminationDto updateExamination(Long id, ExaminationUpdateDto examinationUpdateDto);

    void deleteExamination(Long id);
}
