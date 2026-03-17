package raf.aleksabuncic.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import raf.aleksabuncic.security.Permission;

import java.util.Set;

@Getter
@Setter
@ToString
public class VeterinarianUpdateDto {
    @Size(max = 30)
    @Pattern(regexp = ".*\\S.*")
    private String firstName;

    @Size(max = 30)
    @Pattern(regexp = ".*\\S.*")
    private String lastName;

    @Positive
    private Integer licenseNumber;

    @Email
    private String email;

    @Size(min = 8)
    @ToString.Exclude
    private String password;

    @ToString.Exclude
    private Set<Permission> permissions;
}
