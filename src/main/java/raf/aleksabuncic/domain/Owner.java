package raf.aleksabuncic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 30)
    private String lastName;

    @Column(nullable = false, length = 100)
    @ToString.Exclude
    private String address;

    @Column(length = 15)
    @ToString.Exclude
    private String phoneNumber;

    @Column(unique = true, length = 100)
    @ToString.Exclude
    private String email;

    @Column(nullable = false, unique = true, length = 13)
    @ToString.Exclude
    private String jmbg;
}
