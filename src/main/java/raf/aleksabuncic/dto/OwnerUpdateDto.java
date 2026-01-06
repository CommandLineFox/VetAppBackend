package raf.aleksabuncic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerUpdateDto {
    @Size(min = 30)
    private String firstName;

    @Size(min = 30)
    private String lastName;

    @Size(min = 100)
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{8,15}$")
    private String phoneNumber;

    @Email
    @Size(max = 100)
    private String email;

    @Pattern(regexp = "\\d{13}")
    private String jmbg;
}
