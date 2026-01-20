package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthorizationRequestDto {
    @Positive
    private Integer licenseNumber;

    @NotBlank
    @ToString.Exclude
    private String password;
}
