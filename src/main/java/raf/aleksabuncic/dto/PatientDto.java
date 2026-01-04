package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PatientDto {
    private Long id;
    private String name;
    private Date birthDate;
    private String gender;
    private String passportNumber;
    private String microchipNumber;
    private Integer cartonNumber;
    private Long ownerId;
    private Long breedId;
}
