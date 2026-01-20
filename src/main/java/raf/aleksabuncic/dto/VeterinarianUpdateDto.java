package raf.aleksabuncic.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @Size(min = 8)
    @ToString.Exclude
    private String password;

    @PositiveOrZero
    private Long permissions;
}
