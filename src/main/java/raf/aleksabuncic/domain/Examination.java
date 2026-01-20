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
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Lob
    @ToString.Exclude
    private String anamnesis;

    @Lob
    @ToString.Exclude
    private String clinicalPresentation;

    @Lob
    @ToString.Exclude
    private String diagnosis;

    @Lob
    @ToString.Exclude
    private String treatment;

    @Lob
    @ToString.Exclude
    private String laboratoryAnalysis;

    @Lob
    @ToString.Exclude
    private String specialistExamination;

    @Lob
    @ToString.Exclude
    private String remarks;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_EXAMINATION_PATIENT"))
    @ToString.Exclude
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "FK_EXAMINATION_VETERINARIAN"))
    @ToString.Exclude
    private Veterinarian veterinarian;
}
