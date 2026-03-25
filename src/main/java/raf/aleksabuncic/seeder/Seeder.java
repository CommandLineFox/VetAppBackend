package raf.aleksabuncic.seeder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.*;
import raf.aleksabuncic.repository.*;
import raf.aleksabuncic.security.Permission;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class Seeder implements CommandLineRunner {
    @Value("${seeding.enabled}")
    private boolean shouldSeed;

    private final AppointmentRepository appointmentRepository;
    private final BreedRepository breedRepository;
    private final ExaminationRepository examinationRepository;
    private final OwnerRepository ownerRepository;
    private final PatientRepository patientRepository;
    private final SpeciesRepository speciesRepository;
    private final VeterinarianRepository veterinarianRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (shouldSeed) {
            clearDatabase();

            Veterinarian admin = seedVeterinarians();
            Species dog = seedSpecies();
            Breed breed = seedBreeds(dog);
            Owner owner = seedOwners();
            Patient patient = seedPatients(owner, breed);
            seedExaminations(patient, admin);
            seedAppointments(admin, patient);

            log.info("Database seeding completed successfully.");
        }
    }

    private void clearDatabase() {
        appointmentRepository.deleteAll();
        examinationRepository.deleteAll();
        patientRepository.deleteAll();
        ownerRepository.deleteAll();
        breedRepository.deleteAll();
        speciesRepository.deleteAll();
        veterinarianRepository.deleteAll();

        log.info("Database cleared.");
    }

    private void seedAppointments(Veterinarian vet, Patient patient) {
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDateTime.now().withNano(0).plusDays(1));
        appointment.setDescription("Test Appointment");
        appointment.setPatient(patient);
        appointment.setVeterinarian(vet);
        appointmentRepository.save(appointment);
    }

    private Breed seedBreeds(Species dogSpecies) {
        Breed golden = new Breed();
        golden.setName("Golden Retriever");
        golden.setSpecies(dogSpecies);
        breedRepository.save(golden);

        Breed bulldog = new Breed();
        bulldog.setName("French Bulldog");
        bulldog.setSpecies(dogSpecies);
        breedRepository.save(bulldog);

        log.info("Seeded breeds.");
        return golden;
    }

    private void seedExaminations(Patient patient, Veterinarian vet) {
        Examination ex = new Examination();
        ex.setPatient(patient);
        ex.setDate(LocalDateTime.now().withNano(0));
        ex.setVeterinarian(vet);
        ex.setAnamnesis("Test");
        ex.setDiagnosis("Testing");
        ex.setTreatment("Mhm");
        examinationRepository.save(ex);

        log.info("Seeded examinations.");
    }

    private Owner seedOwners() {
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("B");
        owner.setAddress("Adress");
        owner.setEmail("john@example.com");
        owner.setPhoneNumber("0651122334");
        owner.setJmbg("1602002000000");

        log.info("Seeded owners.");
        return ownerRepository.save(owner);
    }

    private Patient seedPatients(Owner owner, Breed breed) {
        Patient p = new Patient();
        p.setName("Balkan");
        p.setOwner(owner);
        p.setBreed(breed);
        p.setBirthDate(LocalDate.now().minusYears(18));
        p.setGender("M");
        p.setCartonNumber(1);
        p.setPassportNumber("UK12345671");
        p.setMicrochipNumber("111112111111111");
        log.info("Seeded patients.");
        return patientRepository.save(p);
    }

    private Species seedSpecies() {
        Species dog = new Species();
        dog.setName("Dog");
        speciesRepository.save(dog);

        Species cat = new Species();
        cat.setName("Cat");
        speciesRepository.save(cat);

        log.info("Seeded species.");
        return dog;
    }

    private Veterinarian seedVeterinarians() {
        Veterinarian vet1 = new Veterinarian();
        vet1.setLicenseNumber(1);
        vet1.setFirstName("Admin");
        vet1.setLastName("Access");
        vet1.setEmail("test@gmail.com");
        vet1.setPassword(passwordEncoder.encode("testing1"));
        vet1.setPermissions(536870911L);

        veterinarianRepository.save(vet1);

        long limitedPermissions =
                Permission.SPECIES_LIST.getValue()
                        | Permission.SPECIES_ADD.getValue()
                        | Permission.BREED_LIST.getValue()
                        | Permission.OWNER_LIST.getValue();

        Veterinarian vet2 = new Veterinarian();
        vet2.setLicenseNumber(2);
        vet2.setFirstName("Limited");
        vet2.setLastName("Access");
        vet2.setEmail("vet@test.com");
        vet2.setPassword(passwordEncoder.encode("testing2"));
        vet2.setPermissions(limitedPermissions);

        veterinarianRepository.save(vet2);

        log.info("Seeded veterinarians.");
        return vet1;
    }
}