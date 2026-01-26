package raf.aleksabuncic.service.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.VeterinarianCreateDto;
import raf.aleksabuncic.dto.VeterinarianUpdateDto;
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
public class VeterinarianServiceIntegrationTest {
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @BeforeEach
    void setUp() throws Exception {
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
    }

    @Test
    void createVeterinarian() throws Exception {
        VeterinarianCreateDto veterinarianCreateDto = new VeterinarianCreateDto();
        veterinarianCreateDto.setFirstName("Test");
        veterinarianCreateDto.setLastName("Testing");
        veterinarianCreateDto.setLicenseNumber(2);
        veterinarianCreateDto.setPassword("Testing1");
        veterinarianCreateDto.setPermissions(1L);

        mockMvc.perform(post("/veterinarian")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(veterinarianCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(veterinarianCreateDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(veterinarianCreateDto.getLastName()))
                .andExpect(jsonPath("$.licenseNumber").value(veterinarianCreateDto.getLicenseNumber()))
                .andExpect(jsonPath("$.permissions").value(veterinarianCreateDto.getPermissions()));

        Optional<Veterinarian> veterinarian = veterinarianRepository.findByLicenseNumber(2);
        assertTrue(veterinarian.isPresent());
    }

    @Test
    void updateVeterinarian() throws Exception {
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setFirstName("Test");
        veterinarian.setLastName("Testing");
        veterinarian.setLicenseNumber(2);
        veterinarian.setPassword(passwordEncoder.encode("Testing1"));
        veterinarian.setPermissions(1L);
        veterinarianRepository.save(veterinarian);

        VeterinarianUpdateDto veterinarianUpdateDto = new VeterinarianUpdateDto();
        veterinarianUpdateDto.setFirstName("Test1");
        veterinarianUpdateDto.setLastName("Testing1");
        veterinarianUpdateDto.setPassword("Testing2");
        veterinarianUpdateDto.setLicenseNumber(2);
        veterinarianUpdateDto.setPermissions(2L);

        mockMvc.perform(put("/veterinarian/" + veterinarian.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(veterinarianUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(veterinarianUpdateDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(veterinarianUpdateDto.getLastName()))
                .andExpect(jsonPath("$.licenseNumber").value(veterinarianUpdateDto.getLicenseNumber()))
                .andExpect(jsonPath("$.permissions").value(veterinarianUpdateDto.getPermissions()));

        Optional<Veterinarian> updatedVeterinarian = veterinarianRepository.findById(veterinarian.getId());
        assertTrue(updatedVeterinarian.isPresent());
        assertEquals(updatedVeterinarian.get().getPermissions(), veterinarianUpdateDto.getPermissions());
    }

    @Test
    void deleteVeterinarian() throws Exception {
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setFirstName("Test");
        veterinarian.setLastName("Testing");
        veterinarian.setLicenseNumber(2);
        veterinarian.setPassword(passwordEncoder.encode("Testing1"));
        veterinarian.setPermissions(1L);
        veterinarianRepository.save(veterinarian);

        mockMvc.perform(delete("/veterinarian/" + veterinarian.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        Optional<Veterinarian> deletedVeterinarian = veterinarianRepository.findById(veterinarian.getId());
        assertTrue(deletedVeterinarian.isEmpty());
    }
}
