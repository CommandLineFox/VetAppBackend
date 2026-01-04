package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    private String jmbg;
}
