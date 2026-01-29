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
import raf.aleksabuncic.domain.*;
import raf.aleksabuncic.dto.PatientCreateDto;
import raf.aleksabuncic.dto.PatientUpdateDto;
import raf.aleksabuncic.repository.*;

import java.util.Date;
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
public class PatientServiceIntegrationTest {
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
    private BreedRepository breedRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @BeforeEach
    void setUp() throws Exception {
        speciesRepository.deleteAll();
        breedRepository.deleteAll();
        ownerRepository.deleteAll();
        patientRepository.deleteAll();
        veterinarianRepository.deleteAll();

        Veterinarian admin = veterinarianRepository.findByLicenseNumber(1).orElseGet(Veterinarian::new);
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setLicenseNumber(1);
        admin.setEmail("admin@gmail.com");
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

        Breed breed = new Breed();
        breed.setName("Test Breed");
        breed.setSpecies(species);
        breedRepository.save(breed);

        Owner owner = new Owner();
        owner.setFirstName("Test");
        owner.setLastName("Testing");
        owner.setAddress("Far far away");
        owner.setEmail("testing@gmail.com");
        owner.setPhoneNumber("063333333");
        owner.setJmbg("1602002000001");
        ownerRepository.save(owner);
    }

    @Test
    void createPatient() throws Exception {
        Optional<Breed> breed = breedRepository.findByName("Test Breed");
        assertTrue(breed.isPresent());

        Optional<Owner> owner = ownerRepository.findByJmbg("1602002000001");
        assertTrue(owner.isPresent());

        PatientCreateDto patientCreateDto = new PatientCreateDto();
        patientCreateDto.setName("Test Patient");
        patientCreateDto.setGender("M");
        patientCreateDto.setBirthDate(new Date());
        patientCreateDto.setPassportNumber("RS12345678");
        patientCreateDto.setMicrochipNumber("123456789123456");
        patientCreateDto.setCartonNumber(1);
        patientCreateDto.setBreedId(breed.get().getId());
        patientCreateDto.setOwnerId(owner.get().getId());

        mockMvc.perform(post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(patientCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(patientCreateDto.getName()))
                .andExpect(jsonPath("$.gender").value(patientCreateDto.getGender()))
                .andExpect(jsonPath("$.passportNumber").value(patientCreateDto.getPassportNumber()))
                .andExpect(jsonPath("$.microchipNumber").value(patientCreateDto.getMicrochipNumber()))
                .andExpect(jsonPath("$.cartonNumber").value(patientCreateDto.getCartonNumber()))
                .andExpect(jsonPath("$.breedId").value(patientCreateDto.getBreedId().toString()))
                .andExpect(jsonPath("$.ownerId").value(patientCreateDto.getOwnerId().toString()));

        Optional<Patient> patient = patientRepository.findByName(patientCreateDto.getName());
        assertTrue(patient.isPresent());
    }

    @Test
    void updatePatient() throws Exception {
        Optional<Breed> breed = breedRepository.findByName("Test Breed");
        assertTrue(breed.isPresent());

        Breed updatedBreed = new Breed();
        updatedBreed.setName("Updated Test Breed");
        updatedBreed.setSpecies(breed.get().getSpecies());
        breedRepository.save(updatedBreed);

        Optional<Owner> owner = ownerRepository.findByJmbg("1602002000001");
        assertTrue(owner.isPresent());

        Owner updatedOwner = new Owner();
        updatedOwner.setFirstName("Test2");
        updatedOwner.setLastName("Testing2");
        updatedOwner.setAddress("Far far far away");
        updatedOwner.setEmail("testing2@gmail.com");
        updatedOwner.setPhoneNumber("063333334");
        updatedOwner.setJmbg("1602002000002");
        ownerRepository.save(updatedOwner);

        Patient patient = new Patient();
        patient.setName("Test Patient");
        patient.setGender("M");
        patient.setBirthDate(new Date());
        patient.setPassportNumber("RS12345678");
        patient.setMicrochipNumber("123456789123456");
        patient.setCartonNumber(1);
        patient.setBreed(breed.get());
        patient.setOwner(owner.get());
        patientRepository.save(patient);

        PatientUpdateDto patientUpdateDto = new PatientUpdateDto();
        patientUpdateDto.setName("Test Patient Updated");
        patientUpdateDto.setGender("F");
        patientUpdateDto.setBirthDate(new Date());
        patientUpdateDto.setPassportNumber("RS12345719");
        patientUpdateDto.setMicrochipNumber("111111111111111");
        patientUpdateDto.setCartonNumber(2);
        patientUpdateDto.setBreedId(updatedBreed.getId());
        patientUpdateDto.setOwnerId(updatedOwner.getId());

        mockMvc.perform(put("/patient/" + patient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(patientUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patientUpdateDto.getName()))
                .andExpect(jsonPath("$.gender").value(patientUpdateDto.getGender()))
                .andExpect(jsonPath("$.passportNumber").value(patientUpdateDto.getPassportNumber()))
                .andExpect(jsonPath("$.microchipNumber").value(patientUpdateDto.getMicrochipNumber()))
                .andExpect(jsonPath("$.cartonNumber").value(patientUpdateDto.getCartonNumber()))
                .andExpect(jsonPath("$.breedId").value(patientUpdateDto.getBreedId().toString()))
                .andExpect(jsonPath("$.ownerId").value(patientUpdateDto.getOwnerId().toString()));

        Optional<Patient> updatedPatient = patientRepository.findByName(patientUpdateDto.getName());
        assertTrue(updatedPatient.isPresent());
    }

    @Test
    void deletePatient() throws Exception {
        Optional<Breed> breed = breedRepository.findByName("Test Breed");
        assertTrue(breed.isPresent());

        Optional<Owner> owner = ownerRepository.findByJmbg("1602002000001");
        assertTrue(owner.isPresent());

        Patient patient = new Patient();
        patient.setName("Test Patient");
        patient.setGender("M");
        patient.setBirthDate(new Date());
        patient.setPassportNumber("RS12345678");
        patient.setMicrochipNumber("123456789123456");
        patient.setCartonNumber(1);
        patient.setBreed(breed.get());
        patient.setOwner(owner.get());
        patientRepository.save(patient);

        mockMvc.perform(delete("/patient/" + patient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        Optional<Patient> deletedPatient = patientRepository.findByName(patient.getName());
        assertTrue(deletedPatient.isEmpty());
    }
}
