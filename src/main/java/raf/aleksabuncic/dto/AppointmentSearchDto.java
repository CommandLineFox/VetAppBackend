package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class AppointmentSearchDto {
    LocalDate date;
    LocalDate dateFrom;
    LocalDate dateTo;
    String description;
    Long patientId;
    Long veterinarianId;
}
