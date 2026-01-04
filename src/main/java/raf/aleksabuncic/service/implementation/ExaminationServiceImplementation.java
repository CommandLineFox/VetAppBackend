package raf.aleksabuncic.service.implementation;

import org.springframework.stereotype.Service;
import raf.aleksabuncic.dto.ExaminationDto;
import raf.aleksabuncic.dto.ExaminationRequestDto;
import raf.aleksabuncic.service.ExaminationService;

@Service
public class ExaminationServiceImplementation implements ExaminationService {
    @Override
    public ExaminationDto findExaminationById(Long id) {
        return null;
    }

    @Override
    public ExaminationDto createExamination(ExaminationRequestDto examinationRequestDto) {
        return null;
    }

    @Override
    public ExaminationDto updateExamination(ExaminationRequestDto examinationRequestDto) {
        return null;
    }

    @Override
    public void deleteExamination(Long id) {

    }
}
