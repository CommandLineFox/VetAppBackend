package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ExaminationSearchDto {
    LocalDate date;
    LocalDate dateFrom;
    LocalDate dateTo;
    String anamnesis;
    String clinicalPresentation;
    String diagnosis;
    String treatment;
    String laboratoryAnalysis;
    String specialistExamination;
    String remarks;
    Long patientId;
    Long veterinarianId;
}
