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
import raf.aleksabuncic.domain.Species;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.SpeciesCreateDto;
import raf.aleksabuncic.dto.SpeciesUpdateDto;
import raf.aleksabuncic.repository.SpeciesRepository;
import raf.aleksabuncic.repository.VeterinarianRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class SpeciesServiceIntegrationTest {
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @BeforeEach
    void setUp() throws Exception {
        speciesRepository.deleteAll();
        veterinarianRepository.deleteAll();

        Veterinarian admin = new Veterinarian();
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setLicenseNumber(1);
        admin.setPassword(passwordEncoder.encode("Testing"));
        admin.setPermissions(536870911L);
        veterinarianRepository.save(admin);

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("licenseNumber", "1");
        loginRequest.put("password", "Testing");

        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        this.token = JsonPath.read(response, "$.token");
    }

    @Test
    void createSpecies() throws Exception {
        SpeciesCreateDto speciesCreateDto = new SpeciesCreateDto();
        speciesCreateDto.setName("Test Species");

        mockMvc.perform(post("/species")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(speciesCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(speciesCreateDto.getName()));

        Optional<Species> species = speciesRepository.findByName(speciesCreateDto.getName());
        assertTrue(species.isPresent());
    }

    @Test
    void updateSpecies() throws Exception {
        Species species = new Species();
        species.setName("Test Species");
        speciesRepository.save(species);

        SpeciesUpdateDto speciesUpdateDto = new SpeciesUpdateDto();
        speciesUpdateDto.setName("Updated Test Species");

        mockMvc.perform(put("/species/" + species.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(speciesUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(speciesUpdateDto.getName()));

        Optional<Species> updatedSpecies = speciesRepository.findById(species.getId());
        assertTrue(updatedSpecies.isPresent());
        assertEquals(updatedSpecies.get().getName(), speciesUpdateDto.getName());
    }

    @Test
    void deleteSpecies() throws Exception {
        Species species = new Species();
        species.setName("Test Species");
        speciesRepository.save(species);

        mockMvc.perform(delete("/species/" + species.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        Optional<Species> deletedSpecies = speciesRepository.findById(species.getId());
        assertTrue(deletedSpecies.isEmpty());
    }
}
