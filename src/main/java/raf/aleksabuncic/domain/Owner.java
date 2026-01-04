package raf.aleksabuncic.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String firstName;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String lastName;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{8,15}$")
    @Column(length = 15)
    private String phoneNumber;

    @Email
    @Column(unique = true, length = 100)
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{13}")
    @Column(nullable = false, unique = true, length = 13)
    private String jmbg;
}
