package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VeterinarianSearchDto {
    String firstName;
    String lastName;
    Integer licenseNumber;
}
