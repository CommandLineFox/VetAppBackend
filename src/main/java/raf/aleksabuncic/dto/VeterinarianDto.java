package raf.aleksabuncic.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import raf.aleksabuncic.security.Permission;

import java.util.Set;

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
    private Set<Permission> permissions;
}
