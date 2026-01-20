package raf.aleksabuncic.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeterinarianCreateDto {
    @NotBlank
    @Size(max = 30)
    private String firstName;

    @NotBlank
    @Size(max = 30)
    private String lastName;

    @Positive
    private Integer licenseNumber;

    @NotBlank
    @Size(min = 8)
    private String password;

    @PositiveOrZero
    private Long permissions;
}
