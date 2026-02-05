package raf.aleksabuncic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PatientCreateDto {
    @NotBlank
    @Size(max = 30)
    private String name;

    @NotNull
    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

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
