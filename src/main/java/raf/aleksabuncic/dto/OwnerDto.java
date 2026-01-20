package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OwnerDto {
    private Long id;

    private String firstName;

    private String lastName;

    @ToString.Exclude
    private String address;

    @ToString.Exclude
    private String phoneNumber;

    @ToString.Exclude
    private String email;

    @ToString.Exclude
    private String jmbg;
}
