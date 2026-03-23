package raf.aleksabuncic.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AppointmentUpdateDto {
    @FutureOrPresent
    private LocalDateTime date;

    @Pattern(regexp = ".*\\S.*")
    private String description;

    private Long patientId;

    private Long veterinarianId;
}
