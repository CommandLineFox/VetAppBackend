package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeterinarianDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer licenseNumber;
    private String password;
}
