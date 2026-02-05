package raf.aleksabuncic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class ExaminationCreateDto {
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @Pattern(regexp = ".*\\S.*")
    @ToString.Exclude
    private String anamnesis;

    @Pattern(regexp = ".*\\S.*")
    @ToString.Exclude
    private String clinicalPresentation;

    @Pattern(regexp = ".*\\S.*")
    @ToString.Exclude
    private String diagnosis;

    @Pattern(regexp = ".*\\S.*")
    @ToString.Exclude
    private String treatment;

    @Pattern(regexp = ".*\\S.*")
    @ToString.Exclude
    private String laboratoryAnalysis;

    @Pattern(regexp = ".*\\S.*")
    @ToString.Exclude
    private String specialistExamination;

    @Pattern(regexp = ".*\\S.*")
    @ToString.Exclude
    private String remarks;

    @NotNull
    private Long patientId;

    @NotNull
    private Long veterinarianId;
}
