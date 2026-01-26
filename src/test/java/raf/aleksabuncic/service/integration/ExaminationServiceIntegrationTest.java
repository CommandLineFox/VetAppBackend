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
import raf.aleksabuncic.dto.ExaminationCreateDto;
import raf.aleksabuncic.dto.ExaminationUpdateDto;
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
public class ExaminationServiceIntegrationTest {
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
    private ExaminationRepository examinationRepository;

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
    void createExamination() throws Exception {
        Optional<Patient> patient = patientRepository.findByName("Test Patient");
        assertTrue(patient.isPresent());

        Optional<Veterinarian> veterinarian = veterinarianRepository.findByLicenseNumber(1);
        assertTrue(veterinarian.isPresent());

        Date date = new Date();

        ExaminationCreateDto examinationCreateDto = new ExaminationCreateDto();
        examinationCreateDto.setDate(date);
        examinationCreateDto.setDiagnosis("Test Diagnosis");
        examinationCreateDto.setTreatment("Test Treatment");
        examinationCreateDto.setLaboratoryAnalysis("Test Laboratory Analysis");
        examinationCreateDto.setSpecialistExamination("Test Specialist Examination");
        examinationCreateDto.setAnamnesis("Test Anamnesis");
        examinationCreateDto.setClinicalPresentation("Test Clinical Presentation");
        examinationCreateDto.setRemarks("Test Remarks");

        examinationCreateDto.setPatientId(patient.get().getId());
        examinationCreateDto.setVeterinarianId(veterinarian.get().getId());

        mockMvc.perform(post("/examination")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(examinationCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.diagnosis").value(examinationCreateDto.getDiagnosis()))
                .andExpect(jsonPath("$.treatment").value(examinationCreateDto.getTreatment()))
                .andExpect(jsonPath("$.laboratoryAnalysis").value(examinationCreateDto.getLaboratoryAnalysis()))
                .andExpect(jsonPath("$.specialistExamination").value(examinationCreateDto.getSpecialistExamination()))
                .andExpect(jsonPath("$.anamnesis").value(examinationCreateDto.getAnamnesis()))
                .andExpect(jsonPath("$.clinicalPresentation").value(examinationCreateDto.getClinicalPresentation()))
                .andExpect(jsonPath("$.remarks").value(examinationCreateDto.getRemarks()))
                .andExpect(jsonPath("$.patientId").value(patient.get().getId().toString()))
                .andExpect(jsonPath("$.veterinarianId").value(veterinarian.get().getId().toString()));

        Optional<Examination> examination = examinationRepository.findByDateAndPatientAndVeterinarian(date, patient.get(), veterinarian.get());
        assertTrue(examination.isPresent());
    }

    @Test
    void updateExamination() throws Exception {
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

        Examination examination = new Examination();
        examination.setDate(new Date());
        examination.setDiagnosis("Test Diagnosis");
        examination.setTreatment("Test Treatment");
        examination.setLaboratoryAnalysis("Test Laboratory Analysis");
        examination.setSpecialistExamination("Test Specialist Examination");
        examination.setAnamnesis("Test Anamnesis");
        examination.setClinicalPresentation("Test Clinical Presentation");
        examination.setRemarks("Test Remarks");
        examination.setPatient(updatedPatient);
        examination.setVeterinarian(updatedVeterinarian);
        examinationRepository.save(examination);

        Date updatedDate = new Date();

        ExaminationUpdateDto examinationUpdateDto = new ExaminationUpdateDto();
        examinationUpdateDto.setDiagnosis("Test Diagnosis Updated");
        examinationUpdateDto.setTreatment("Test Treatment Updated");
        examinationUpdateDto.setLaboratoryAnalysis("Test Laboratory Analysis Updated");
        examinationUpdateDto.setAnamnesis("Test Anamnesis Updated");
        examinationUpdateDto.setSpecialistExamination("Test Specialist Examination Updated");
        examinationUpdateDto.setDate(updatedDate);
        examinationUpdateDto.setRemarks("Test Remarks Updated");
        examinationUpdateDto.setClinicalPresentation("Test Clinical Presentation Updated");
        examinationUpdateDto.setPatientId(updatedPatient.getId());
        examinationUpdateDto.setVeterinarianId(updatedVeterinarian.getId());

        mockMvc.perform(put("/examination/" + examination.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(examinationUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosis").value(examinationUpdateDto.getDiagnosis()))
                .andExpect(jsonPath("$.treatment").value(examinationUpdateDto.getTreatment()))
                .andExpect(jsonPath("$.laboratoryAnalysis").value(examinationUpdateDto.getLaboratoryAnalysis()))
                .andExpect(jsonPath("$.specialistExamination").value(examinationUpdateDto.getSpecialistExamination()))
                .andExpect(jsonPath("$.anamnesis").value(examinationUpdateDto.getAnamnesis()))
                .andExpect(jsonPath("$.clinicalPresentation").value(examinationUpdateDto.getClinicalPresentation()))
                .andExpect(jsonPath("$.remarks").value(examinationUpdateDto.getRemarks()))
                .andExpect(jsonPath("$.patientId").value(updatedPatient.getId().toString()))
                .andExpect(jsonPath("$.veterinarianId").value(updatedVeterinarian.getId().toString()));

        Optional<Examination> updatedExamination = examinationRepository.findByDateAndPatientAndVeterinarian(updatedDate, updatedPatient, updatedVeterinarian);
        assertTrue(updatedExamination.isPresent());
    }

    @Test
    void deleteExamination() throws Exception {
        Optional<Patient> patient = patientRepository.findByName("Test Patient");
        assertTrue(patient.isPresent());

        Optional<Breed> breed = breedRepository.findByName("Test Breed");
        assertTrue(breed.isPresent());

        Optional<Owner> owner = ownerRepository.findByJmbg("1602002000001");
        assertTrue(owner.isPresent());

        Optional<Veterinarian> veterinarian = veterinarianRepository.findByLicenseNumber(1);
        assertTrue(veterinarian.isPresent());


        Examination examination = new Examination();
        examination.setDate(new Date());
        examination.setDiagnosis("Test Diagnosis");
        examination.setTreatment("Test Treatment");
        examination.setLaboratoryAnalysis("Test Laboratory Analysis");
        examination.setSpecialistExamination("Test Specialist Examination");
        examination.setAnamnesis("Test Anamnesis");
        examination.setClinicalPresentation("Test Clinical Presentation");
        examination.setRemarks("Test Remarks");
        examination.setPatient(patient.get());
        examination.setVeterinarian(veterinarian.get());
        examinationRepository.save(examination);

        mockMvc.perform(delete("/examination/" + examination.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        Optional<Examination> deletedExamination = examinationRepository.findById(examination.getId());
        assertTrue(deletedExamination.isEmpty());
    }
}
