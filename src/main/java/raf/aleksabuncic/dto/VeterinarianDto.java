package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VeterinarianDto {
    private Long id;

    private String firstName;

    private String lastName;

    private Integer licenseNumber;

    @ToString.Exclude
    private String password;

    private Long permissions;
}
