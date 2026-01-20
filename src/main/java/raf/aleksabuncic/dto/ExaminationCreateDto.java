package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ExaminationCreateDto {
    @NotNull
    private Date date;

    @ToString.Exclude
    private String anamnesis;

    @ToString.Exclude
    private String clinicalPresentation;

    @ToString.Exclude
    private String diagnosis;

    @ToString.Exclude
    private String treatment;

    @ToString.Exclude
    private String laboratoryAnalysis;

    @ToString.Exclude
    private String specialistExamination;

    @ToString.Exclude
    private String remarks;

    @NotNull
    private Long patientId;

    @NotNull
    private Long veterinarianId;
}
