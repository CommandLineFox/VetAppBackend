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
import raf.aleksabuncic.domain.Owner;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.dto.OwnerCreateDto;
import raf.aleksabuncic.dto.OwnerUpdateDto;
import raf.aleksabuncic.repository.OwnerRepository;
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
public class OwnerServiceIntegrationTest {
    private String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @BeforeEach
    void setUp() throws Exception {
        ownerRepository.deleteAll();
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
    void createOwner() throws Exception {
        OwnerCreateDto ownerCreateDto = new OwnerCreateDto();
        ownerCreateDto.setFirstName("Test");
        ownerCreateDto.setLastName("Testing");
        ownerCreateDto.setAddress("Far far away");
        ownerCreateDto.setEmail("testing@gmail.com");
        ownerCreateDto.setPhoneNumber("063333333");
        ownerCreateDto.setJmbg("1602002000001");

        mockMvc.perform(post("/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(ownerCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(ownerCreateDto.getFirstName()));

        Optional<Owner> owner = ownerRepository.findByJmbg(ownerCreateDto.getJmbg());
        assertTrue(owner.isPresent());
    }

    @Test
    void updateOwner() throws Exception {
        Owner owner = new Owner();
        owner.setFirstName("Test");
        owner.setLastName("Testing");
        owner.setAddress("Far far away");
        owner.setEmail("testing@gmail.com");
        owner.setPhoneNumber("063333333");
        owner.setJmbg("1602002000001");
        ownerRepository.save(owner);

        OwnerUpdateDto ownerUpdateDto = new OwnerUpdateDto();
        ownerUpdateDto.setFirstName("Test1");
        ownerUpdateDto.setLastName("Testing1");
        ownerUpdateDto.setAddress("Further away");
        ownerUpdateDto.setEmail("testing2@gmail.com");
        ownerUpdateDto.setPhoneNumber("063333334");
        ownerUpdateDto.setJmbg("1602002000002");

        mockMvc.perform(put("/owner/" + owner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(ownerUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(ownerUpdateDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(ownerUpdateDto.getLastName()))
                .andExpect(jsonPath("$.address").value(ownerUpdateDto.getAddress()))
                .andExpect(jsonPath("$.email").value(ownerUpdateDto.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(ownerUpdateDto.getPhoneNumber()))
                .andExpect(jsonPath("$.jmbg").value(ownerUpdateDto.getJmbg()));

        Optional<Owner> updatedOwner = ownerRepository.findById(owner.getId());
        assertTrue(updatedOwner.isPresent());
    }

    @Test
    void deleteOwner() throws Exception {
        Owner owner = new Owner();
        owner.setFirstName("Test");
        owner.setLastName("Testing");
        owner.setAddress("Far far away");
        owner.setEmail("testing@gmail.com");
        owner.setPhoneNumber("063333333");
        owner.setJmbg("1602002000001");
        ownerRepository.save(owner);

        mockMvc.perform(delete("/owner/" + owner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        Optional<Owner> deletedOwner = ownerRepository.findById(owner.getId());
        assertTrue(deletedOwner.isEmpty());
    }
}
