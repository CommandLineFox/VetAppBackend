package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.ExaminationDto;
import raf.aleksabuncic.dto.ExaminationRequestDto;

public interface ExaminationService {
    ExaminationDto findExaminationById(Long id);

    ExaminationDto createExamination(ExaminationRequestDto examinationRequestDto);

    ExaminationDto updateExamination(ExaminationRequestDto examinationRequestDto);

    void deleteExamination(Long id);
}
