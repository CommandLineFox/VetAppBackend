package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AppointmentDto {
    private Long id;

    private LocalDateTime date;

    private String description;

    private PatientDisplayDto patient;

    private VeterinarianDisplayDto veterinarian;
}
