package raf.aleksabuncic.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.repository.VeterinarianRepository;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {
    private final VeterinarianRepository veterinarianRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (veterinarianRepository.count() == 0) {
            Veterinarian vet1 = new Veterinarian();
            vet1.setLicenseNumber(1);
            vet1.setFirstName("Tatjana");
            vet1.setLastName("Buncic");
            vet1.setPassword(passwordEncoder.encode("testiranje"));
            vet1.setPermissions(536870911L);

            veterinarianRepository.save(vet1);

            System.out.println("Database seeded");
        }
    }
}