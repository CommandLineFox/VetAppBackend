package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AppointmentUpdateDto {
    private Date date;
    private Long patientId;
    private Long veterinarianId;
}
