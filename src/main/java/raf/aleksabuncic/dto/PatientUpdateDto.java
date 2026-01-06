package raf.aleksabuncic.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PatientUpdateDto {
    @Size(max = 30)
    private String name;

    private Date birthDate;

    @Pattern(regexp = "^([MF])$")
    private String gender;

    @Pattern(regexp = "^[A-Z]{2}\\d{8}$")
    private String passportNumber;

    @Pattern(regexp = "^\\d{8,15}$")
    private String microchipNumber;

    private Integer cartonNumber;
    private Long ownerId;
    private Long breedId;
}
