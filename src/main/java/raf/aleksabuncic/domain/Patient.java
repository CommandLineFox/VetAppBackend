package raf.aleksabuncic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false, length = 1)
    private String gender;

    @Column(nullable = false, unique = true, length = 10)
    private String passportNumber;

    @Column(nullable = false, unique = true, length = 15)
    private String microchipNumber;

    @Column(nullable = false)
    private Integer cartonNumber;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_PATIENT_OWNER"))
    @ToString.Exclude
    private Owner owner;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_PATIENT_BREED"))
    @ToString.Exclude
    private Breed breed;
}
