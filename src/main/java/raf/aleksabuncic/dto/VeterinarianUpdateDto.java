package raf.aleksabuncic.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VeterinarianUpdateDto {
    @Size(max = 30)
    private String firstName;

    @Size(max = 30)
    private String lastName;

    @Positive
    private Integer licenseNumber;

    @Email
    private String email;

    @Size(min = 8)
    @ToString.Exclude
    private String password;

    @PositiveOrZero
    @ToString.Exclude
    private Long permissions;
}
