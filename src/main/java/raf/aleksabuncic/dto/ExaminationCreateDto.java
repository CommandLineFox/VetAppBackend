package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExaminationCreateDto {
    @NotNull
    private Date date;
    private String anamnesis;
    private String clinicalPresentation;
    private String diagnosis;
    private String treatment;
    private String laboratoryAnalysis;
    private String specialistExamination;
    private String remarks;

    @NotNull
    private Long patientId;

    @NotNull
    private Long veterinarianId;
}
