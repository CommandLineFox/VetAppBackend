package raf.aleksabuncic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 30)
    private String lastName;

    @Column(nullable = false, unique = true)
    private Integer licenseNumber;

    @Column(nullable = false)
    @ToString.Exclude
    private String password;

    @Column(nullable = false)
    private Long permissions;
}
