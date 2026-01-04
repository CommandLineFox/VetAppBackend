package raf.aleksabuncic.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Date birthDate;

    @NotBlank
    @Pattern(regexp = "^(M|F)$")
    @Column(nullable = false, length = 1)
    private String gender;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}\\d{8}$")
    @Column(nullable = false, unique = true, length = 10)
    private String passportNumber;

    @NotBlank
    @Pattern(regexp = "^\\d{8,15}$")
    @Column(nullable = false, unique = true, length = 15)
    private String microchipNumber;

    @NotNull
    @Column(nullable = false)
    private Integer cartonNumber;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_PATIENT_OWNER"))
    private Owner owner;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_PATIENT_BREED"))
    private Breed breed;
}
