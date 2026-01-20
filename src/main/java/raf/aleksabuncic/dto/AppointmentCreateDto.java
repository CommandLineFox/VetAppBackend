package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AppointmentCreateDto {
    @NotNull
    private Date date;

    @NotNull
    private Long patientId;

    @NotNull
    private Long veterinarianId;
}
