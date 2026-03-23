package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AppointmentCreateDto {
    @NotNull
    private LocalDateTime date;

    @NotBlank
    private String description;

    @NotNull
    private Long patientId;

    @NotNull
    private Long veterinarianId;
}
