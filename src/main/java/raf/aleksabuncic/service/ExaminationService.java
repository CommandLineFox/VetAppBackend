package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.ExaminationDto;
import raf.aleksabuncic.dto.ExaminationCreateDto;
import raf.aleksabuncic.dto.ExaminationUpdateDto;

public interface ExaminationService {
    ExaminationDto findExaminationById(Long id);

    ExaminationDto createExamination(ExaminationCreateDto examinationCreateDto);

    ExaminationDto updateExamination(ExaminationUpdateDto examinationUpdateDto);

    void deleteExamination(Long id);
}
