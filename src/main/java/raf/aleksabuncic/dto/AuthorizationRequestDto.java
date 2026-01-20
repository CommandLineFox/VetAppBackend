package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationRequestDto {
    @Positive
    private Integer licenseNumber;

    @NotBlank
    private String password;
}
