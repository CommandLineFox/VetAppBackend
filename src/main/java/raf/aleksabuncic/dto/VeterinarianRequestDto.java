package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeterinarianRequestDto {
    private String firstName;
    private String lastName;
    private Integer licenseNumber;
    private String password;
}
