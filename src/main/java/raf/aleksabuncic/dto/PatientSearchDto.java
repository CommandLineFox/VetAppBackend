package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PatientSearchDto {
    private String name;
    private String passportNumber;
    private String microchipNumber;
    private Long ownerId;
    private Long breedId;
    private LocalDate birthDate;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
}
