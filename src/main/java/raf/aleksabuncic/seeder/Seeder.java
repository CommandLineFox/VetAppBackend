package raf.aleksabuncic.seeder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.repository.VeterinarianRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class Seeder implements CommandLineRunner {
    private final VeterinarianRepository veterinarianRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        veterinarianRepository.deleteAll();
        if (veterinarianRepository.count() == 0) {
            Veterinarian vet1 = new Veterinarian();
            vet1.setLicenseNumber(1);
            vet1.setFirstName("Admin");
            vet1.setLastName("Access");
            vet1.setEmail("test@gmail.com");
            vet1.setPassword(passwordEncoder.encode("testing1"));
            vet1.setPermissions(536870911L);

            veterinarianRepository.save(vet1);

            log.info("Database seeded");
        }
    }
}