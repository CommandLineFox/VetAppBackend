package raf.aleksabuncic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthorizationResponseDto {
    @NotBlank
    @ToString.Exclude
    private String token;
}
