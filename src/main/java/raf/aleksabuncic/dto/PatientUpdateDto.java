package raf.aleksabuncic.dto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PatientUpdateDto {
    @Size(max = 30)
    @Pattern(regexp = ".*\\S.*")
    private String name;

    @PastOrPresent
    private LocalDate birthDate;

    @Pattern(regexp = "^([MF])$")
    private String gender;

    @Pattern(regexp = "^[A-Z]{2}\\d{8}$")
    private String passportNumber;

    @Pattern(regexp = "^\\d{8,15}$")
    private String microchipNumber;

    @Positive
    private Integer cartonNumber;
    private Long ownerId;
    private Long breedId;
}
