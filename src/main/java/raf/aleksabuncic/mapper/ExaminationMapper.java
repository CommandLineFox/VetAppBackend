package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Examination;
import raf.aleksabuncic.dto.ExaminationRequestDto;
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

    public Examination examinationCRequestDtoToExamination(ExaminationRequestDto examinationRequestDto) {
        Examination examination = new Examination();

        examination.setDate(examinationRequestDto.getDate());
        examination.setAnamnesis(examinationRequestDto.getAnamnesis());
        examination.setClinicalPresentation(examinationRequestDto.getClinicalPresentation());
        examination.setDiagnosis(examinationRequestDto.getDiagnosis());
        examination.setTreatment(examinationRequestDto.getTreatment());
        examination.setLaboratoryAnalysis(examinationRequestDto.getLaboratoryAnalysis());
        examination.setSpecialistExamination(examinationRequestDto.getSpecialistExamination());
        examination.setRemarks(examinationRequestDto.getRemarks());

        return examination;
    }
}
