package raf.aleksabuncic.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeterinarianUpdateDto {
    @Size(max = 30)
    private String firstName;

    @Size(max = 30)
    private String lastName;

    private Integer licenseNumber;

    @Size(min = 8)
    private String password;
}
