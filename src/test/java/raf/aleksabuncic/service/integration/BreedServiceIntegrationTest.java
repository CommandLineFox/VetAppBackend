package raf.aleksabuncic.service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import raf.aleksabuncic.domain.Breed;
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.BreedUpdateDto;
import raf.aleksabuncic.repository.BreedRepository;
import raf.aleksabuncic.repository.SpeciesRepository;
import raf.aleksabuncic.repository.VeterinarianRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class BreedServiceIntegrationTest {
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @BeforeEach
    void setUp() throws Exception {
        speciesRepository.deleteAll();
        breedRepository.deleteAll();
        veterinarianRepository.deleteAll();

        Veterinarian admin = veterinarianRepository.findByLicenseNumber(1).orElseGet(Veterinarian::new);
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setLicenseNumber(1);
        admin.setPassword(passwordEncoder.encode("testing1"));
        admin.setPermissions(536870911L);
        veterinarianRepository.save(admin);

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("licenseNumber", "1");
        loginRequest.put("password", "testing1");

        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        this.token = JsonPath.read(response, "$.token");

        Species species = new Species();
        species.setName("Test Species");
        speciesRepository.save(species);
    }

    @Test
    void createBreed() throws Exception {
        Optional<Species> species = speciesRepository.findByName("Test Species");
        assertTrue(species.isPresent());

        Map<String, String> breedRequest = new HashMap<>();
        breedRequest.put("name", "Test Breed");
        breedRequest.put("speciesId", species.get().getId().toString());

        mockMvc.perform(post("/breed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(breedRequest)))
                .andExpect(status().isCreated());

        Optional<Breed> breed = breedRepository.findByName("Test Breed");
        assertTrue(breed.isPresent());
    }

    @Test
    void updateBreed() throws Exception {
        Optional<Species> species = speciesRepository.findByName("Test Species");
        assertTrue(species.isPresent());

        Species updatedSpecies = new Species();
        updatedSpecies.setName("Test Updated Species");
        speciesRepository.save(updatedSpecies);

        Breed breed = new Breed();
        breed.setName("Test breed");
        breed.setSpecies(species.get());
        breedRepository.save(breed);

        BreedUpdateDto breedUpdateDto = new BreedUpdateDto();
        breedUpdateDto.setName("Updated Test breed");
        breedUpdateDto.setSpeciesId(updatedSpecies.getId());

        mockMvc.perform(put("/breed/" + breed.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(breedUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(breedUpdateDto.getName()))
                .andExpect(jsonPath("$.speciesId").value(breedUpdateDto.getSpeciesId().toString()));

        Optional<Breed> updatedBreed = breedRepository.findByName("Updated Test breed");
        assertTrue(updatedBreed.isPresent());
    }

    @Test
    void deleteBreed() throws Exception {
        Optional<Species> species = speciesRepository.findByName("Test Species");
        assertTrue(species.isPresent());

        Breed breed = new Breed();
        breed.setName("Test breed");
        breed.setSpecies(species.get());
        breedRepository.save(breed);

        mockMvc.perform(delete("/breed/" + breed.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        Optional<Breed> deletedBreed = breedRepository.findById(breed.getId());
        assertTrue(deletedBreed.isEmpty());
    }
}
