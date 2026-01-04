package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Examination;
import raf.aleksabuncic.dto.ExaminationDto;

@Component
public class ExaminationMapper {
    public ExaminationDto examinationToExaminationDto(Examination examination) {
        ExaminationDto examinationDto = new ExaminationDto();

        examinationDto.setId(examination.getId());
        examinationDto.setDate(examination.getDate());
        examinationDto.setAnamnesis(examination.getAnamnesis());
        examinationDto.setClinicalPresentation(examination.getClinicalPresentation());
        examinationDto.setDiagnosis(examination.getDiagnosis());
        examinationDto.setTreatment(examination.getTreatment());
        examinationDto.setLaboratoryAnalysis(examination.getLaboratoryAnalysis());
        examinationDto.setSpecialistExamination(examination.getSpecialistExamination());
        examinationDto.setRemarks(examination.getRemarks());

        return examinationDto;
    }
}
