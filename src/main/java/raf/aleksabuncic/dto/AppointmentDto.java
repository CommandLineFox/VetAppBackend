package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppointmentDto {
    private Long id;
    private Date date;
    private Long patientId;
    private Long veterinarianId;
}
