package raf.aleksabuncic.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotNull
    @Column(nullable = false, unique = true)
    private Integer licenseNumber;

    @NotBlank
    @Column(nullable = false)
    private String password;
}
