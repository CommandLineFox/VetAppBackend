package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExaminationRequestDto {
    private Date date;
    private String anamnesis;
    private String clinicalPresentation;
    private String diagnosis;
    private String treatment;
    private String laboratoryAnalysis;
    private String specialistExamination;
    private String remarks;
    private Long patientId;
    private Long veterinarianId;
}
