package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationGoogleRequestDto {
    @NotBlank
    private String token;
}
