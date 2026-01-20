package raf.aleksabuncic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OwnerUpdateDto {
    @Size(min = 30)
    private String firstName;

    @Size(min = 30)
    private String lastName;

    @Size(min = 100)
    @ToString.Exclude
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{8,15}$")
    @ToString.Exclude
    private String phoneNumber;

    @Email
    @Size(max = 100)
    @ToString.Exclude
    private String email;

    @Pattern(regexp = "\\d{13}")
    @ToString.Exclude
    private String jmbg;
}
