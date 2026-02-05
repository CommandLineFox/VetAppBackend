package raf.aleksabuncic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ExaminationUpdateDto {
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

    private Long patientId;

    private Long veterinarianId;
}
