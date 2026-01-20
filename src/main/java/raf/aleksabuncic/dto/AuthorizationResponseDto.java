package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationResponseDto {
    @NotBlank
    private String token;
}
