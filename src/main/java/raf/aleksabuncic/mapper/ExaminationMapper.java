package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Examination;
import raf.aleksabuncic.dto.ExaminationCreateDto;
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
        examinationDto.setPatientId(examination.getPatient().getId());
        examinationDto.setVeterinarianId(examination.getVeterinarian().getId());

        return examinationDto;
    }

    public Examination examinationCreateDtoToExamination(ExaminationCreateDto examinationCreateDto) {
        Examination examination = new Examination();

        examination.setDate(examinationCreateDto.getDate());
        examination.setAnamnesis(examinationCreateDto.getAnamnesis());
        examination.setClinicalPresentation(examinationCreateDto.getClinicalPresentation());
        examination.setDiagnosis(examinationCreateDto.getDiagnosis());
        examination.setTreatment(examinationCreateDto.getTreatment());
        examination.setLaboratoryAnalysis(examinationCreateDto.getLaboratoryAnalysis());
        examination.setSpecialistExamination(examinationCreateDto.getSpecialistExamination());
        examination.setRemarks(examinationCreateDto.getRemarks());

        return examination;
    }
}
