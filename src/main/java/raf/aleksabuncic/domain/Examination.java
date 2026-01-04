package raf.aleksabuncic.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Date date;

    @Lob
    private String anamnesis;

    @Lob
    private String clinicalPresentation;

    @Lob
    private String diagnosis;

    @Lob
    private String treatment;

    @Lob
    private String laboratoryAnalysis;

    @Lob
    private String specialistExamination;

    @Lob
    private String remarks;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_EXAMINATION_PATIENT"))
    private Patient patient;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_EXAMINATION_VETERINARIAN"))
    private Veterinarian veterinarian;
}
