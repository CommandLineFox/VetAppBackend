package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PatientCreateDto {
    @NotBlank
    @Size(max = 30)
    private String name;

    @NotNull
    private Date birthDate;

    @NotBlank
    @Pattern(regexp = "^([MF])$")
    private String gender;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}\\d{8}$")
    private String passportNumber;

    @NotBlank
    @Pattern(regexp = "^\\d{8,15}$")
    private String microchipNumber;

    @NotNull
    private Integer cartonNumber;

    @NotNull
    private Long ownerId;

    @NotNull
    private Long breedId;
}
