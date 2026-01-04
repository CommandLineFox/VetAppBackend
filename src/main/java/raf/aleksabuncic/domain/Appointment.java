package raf.aleksabuncic.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Date date;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_APPOINTMENT_VETERINARIAN"))
    private Veterinarian veterinarian;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_APPOINTMENT_PATIENT"))
    private Patient patient;
}
