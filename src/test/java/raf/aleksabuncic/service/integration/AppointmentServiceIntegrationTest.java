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
import raf.aleksabuncic.dto.AppointmentCreateDto;
import raf.aleksabuncic.dto.AppointmentUpdateDto;
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
public class AppointmentServiceIntegrationTest {
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
    private AppointmentRepository appointmentRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @BeforeEach
    void setUp() throws Exception {
        speciesRepository.deleteAll();
        breedRepository.deleteAll();
        ownerRepository.deleteAll();
        patientRepository.deleteAll();
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

        Patient patient = new Patient();
        patient.setName("Test Patient");
        patient.setGender("M");
        patient.setBirthDate(new Date());
        patient.setPassportNumber("RS12345678");
        patient.setMicrochipNumber("123456789123456");
        patient.setCartonNumber(1);
        patient.setBreed(breed);
        patient.setOwner(owner);
        patientRepository.save(patient);
    }

    @Test
    void createAppointment() throws Exception {
        Optional<Patient> patient = patientRepository.findByName("Test Patient");
        assertTrue(patient.isPresent());

        Optional<Veterinarian> veterinarian = veterinarianRepository.findByLicenseNumber(1);
        assertTrue(veterinarian.isPresent());

        Date date = new Date();

        AppointmentCreateDto appointmentCreateDto = new AppointmentCreateDto();
        appointmentCreateDto.setDate(date);
        appointmentCreateDto.setPatientId(patient.get().getId());
        appointmentCreateDto.setVeterinarianId(veterinarian.get().getId());

        mockMvc.perform(post("/appointment")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentCreateDto)))
                .andExpect(status().isCreated())
                .andExpect((jsonPath("$.patientId").value(appointmentCreateDto.getPatientId().toString())))
                .andExpect((jsonPath("$.veterinarianId").value(appointmentCreateDto.getVeterinarianId().toString())));

        Optional<Appointment> appointment = appointmentRepository.findByDateAndPatientAndVeterinarian(date, patient.get(), veterinarian.get());
        assertTrue(appointment.isPresent());
    }

    @Test
    void updateAppointment() throws Exception {
        Optional<Patient> patient = patientRepository.findByName("Test Patient");
        assertTrue(patient.isPresent());

        Optional<Breed> breed = breedRepository.findByName("Test Breed");
        assertTrue(breed.isPresent());

        Optional<Owner> owner = ownerRepository.findByJmbg("1602002000001");
        assertTrue(owner.isPresent());

        Patient updatedPatient = new Patient();
        updatedPatient.setName("Test Patient Updated");
        updatedPatient.setGender("F");
        updatedPatient.setBirthDate(new Date());
        updatedPatient.setPassportNumber("RS12345672");
        updatedPatient.setMicrochipNumber("111111111111111");
        updatedPatient.setCartonNumber(2);
        updatedPatient.setBreed(breed.get());
        updatedPatient.setOwner(owner.get());
        patientRepository.save(updatedPatient);

        Optional<Veterinarian> veterinarian = veterinarianRepository.findByLicenseNumber(1);
        assertTrue(veterinarian.isPresent());

        Veterinarian updatedVeterinarian = new Veterinarian();
        updatedVeterinarian.setFirstName("Test");
        updatedVeterinarian.setLastName("Testing");
        updatedVeterinarian.setLicenseNumber(2);
        updatedVeterinarian.setPassword(passwordEncoder.encode("Testing1"));
        updatedVeterinarian.setPermissions(1L);
        veterinarianRepository.save(updatedVeterinarian);

        Appointment appointment = new Appointment();
        appointment.setDate(new Date());
        appointment.setPatient(patient.get());
        appointment.setVeterinarian(veterinarian.get());
        appointmentRepository.save(appointment);

        Date updatedDate = new Date();

        AppointmentUpdateDto appointmentUpdateDto = new AppointmentUpdateDto();
        appointmentUpdateDto.setDate(updatedDate);
        appointmentUpdateDto.setPatientId(updatedPatient.getId());
        appointmentUpdateDto.setVeterinarianId(updatedVeterinarian.getId());

        mockMvc.perform(put("/appointment/" + appointment.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentUpdateDto)))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.patientId").value(appointmentUpdateDto.getPatientId().toString())))
                .andExpect((jsonPath("$.veterinarianId").value(appointmentUpdateDto.getVeterinarianId().toString())));

        Optional<Appointment> updatedAppointment = appointmentRepository.findByDateAndPatientAndVeterinarian(updatedDate, updatedPatient, updatedVeterinarian);
        assertTrue(updatedAppointment.isPresent());
    }

    @Test
    void deleteAppointment() throws Exception {
        Optional<Patient> patient = patientRepository.findByName("Test Patient");
        assertTrue(patient.isPresent());

        Optional<Breed> breed = breedRepository.findByName("Test Breed");
        assertTrue(breed.isPresent());

        Optional<Owner> owner = ownerRepository.findByJmbg("1602002000001");
        assertTrue(owner.isPresent());

        Optional<Veterinarian> veterinarian = veterinarianRepository.findByLicenseNumber(1);
        assertTrue(veterinarian.isPresent());

        Appointment appointment = new Appointment();
        appointment.setDate(new Date());
        appointment.setPatient(patient.get());
        appointment.setVeterinarian(veterinarian.get());
        appointmentRepository.save(appointment);

        mockMvc.perform(delete("/appointment/" + appointment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        Optional<Appointment> deletedAppointment = appointmentRepository.findById(appointment.getId());
        assertTrue(deletedAppointment.isEmpty());
    }
}
