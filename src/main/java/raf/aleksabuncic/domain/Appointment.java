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
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_APPOINTMENT_VETERINARIAN"))
    @ToString.Exclude
    private Veterinarian veterinarian;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_APPOINTMENT_PATIENT"))
    @ToString.Exclude
    private Patient patient;
}
