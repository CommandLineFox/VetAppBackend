package raf.aleksabuncic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @Email
    private String email;

    @ToString.Exclude
    private String password;

    @ToString.Exclude
    private Long permissions;
}
