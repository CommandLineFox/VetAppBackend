package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppointmentCreateDto {
    @NotNull
    private Date date;

    @NotNull
    private Long patientId;

    @NotNull
    private Long veterinarianId;
}
