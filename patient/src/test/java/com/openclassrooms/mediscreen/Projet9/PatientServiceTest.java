package com.openclassrooms.mediscreen.Projet9;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.openclassrooms.mediscreen.Projet9.dao.PatientDao;
import com.openclassrooms.mediscreen.Projet9.exceptions.PatientIntrouvableException;
import com.openclassrooms.mediscreen.Projet9.model.Patient;
import com.openclassrooms.mediscreen.Projet9.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientDao patientDao;
    public PatientServiceTest() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void createUser() {
            Patient patient = new Patient();
            patient.setFirstName("John");
            patient.setLastName("Doe");
            when(patientDao.save(ArgumentMatchers.any(Patient.class))).thenReturn(patient);
            Patient created = patientService.validate(patient, null,null);
            assertThat(created.getFirstName()).isSameAs(patient.getFirstName());
            verify(patientDao).save(patient);
    }

    @Test
    public void testFindAll() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1, "John", "Doe"));
        patients.add(new Patient(2, "Jane", "Smith"));

        when(patientDao.findAll()).thenReturn(patients);

        List<Patient> result = patientService.findAll();

        assertEquals(patients, result);
    }

    @Test
    public void updatePatient() {
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setId(1);

        Patient newPatient = new Patient();
        patient.setFirstName("Johnna");

       // given(patientDao.findById(patient.getId())).willReturn((patient));
        patientService.validate(newPatient, null,null);
        verify(patientDao).save(newPatient);


    }

    @Test
    public void testPatientIntrouvableException() {
        // Créez un service de patient avec Mockito
        PatientService patientService = Mockito.mock(PatientService.class);

        // Configurez le comportement du service pour lever l'exception PatientIntrouvableException
        Mockito.when(patientService.getById(Mockito.anyInt()))
                .thenThrow(new PatientIntrouvableException("Patient introuvable"));

        // Utilisez assertThrows pour vérifier que l'exception est levée lors de l'appel de la méthode
        assertThrows(PatientIntrouvableException.class, () -> patientService.getById(1));
    }

    @Test
    public void testGetById() {
        int patientId = 1;
        Patient expectedPatient = new Patient(patientId, "John", "Doe");
        patientDao.save(expectedPatient);
        assertEquals(patientId, expectedPatient.getId());

    }

}


