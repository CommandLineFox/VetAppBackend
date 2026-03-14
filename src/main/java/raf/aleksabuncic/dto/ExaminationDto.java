package raf.aleksabuncic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ExaminationDto {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

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

    private PatientDisplayDto patient;

    private VeterinarianDisplayDto veterinarian;
}
