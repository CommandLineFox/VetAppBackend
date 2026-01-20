package raf.aleksabuncic.security;

import lombok.Getter;

@Getter
public enum Permission {
    SPECIES_LIST(1L),
    SPECIES_ADD(1L << 1),
    SPECIES_UPDATE(1L << 2),
    SPECIES_DELETE(1L << 3),
    BREED_LIST(1L << 4),
    BREED_ADD(1L << 5),
    BREED_UPDATE(1L << 6),
    BREED_DELETE(1L << 7),
    OWNER_LIST(1L << 8),
    OWNER_ADD(1L << 9),
    OWNER_UPDATE(1L << 10),
    OWNER_DELETE(1L << 11),
    PATIENT_LIST(1L << 12),
    PATIENT_ADD(1L << 13),
    PATIENT_UPDATE(1L << 14),
    PATIENT_DELETE(1L << 15),
    EXAMINATION_LIST(1L << 16),
    EXAMINATION_ADD(1L << 17),
    EXAMINATION_UPDATE(1L << 18),
    EXAMINATION_DELETE(1L << 19),
    APPOINTMENT_LIST(1L << 20),
    APPOINTMENT_ADD(1L << 21),
    APPOINTMENT_UPDATE(1L << 22),
    APPOINTMENT_DELETE(1L << 23),
    VETERINARIAN_LIST(1L << 24),
    VETERINARIAN_ADD(1L << 25),
    VETERINARIAN_UPDATE(1L << 26),
    VETERINARIAN_DELETE(1L << 27),
    ADMIN(1L << 28);

    private final long value;

    Permission(long value) {
        this.value = value;
    }
}
