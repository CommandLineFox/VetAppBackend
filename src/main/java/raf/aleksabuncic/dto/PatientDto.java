package raf.aleksabuncic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PatientDto {
    private Long id;

    private String name;

    private LocalDate birthDate;

    private String gender;

    private String passportNumber;

    private String microchipNumber;

    private Integer cartonNumber;

    private OwnerDisplayDto owner;

    private BreedDisplayDto breed;
}
