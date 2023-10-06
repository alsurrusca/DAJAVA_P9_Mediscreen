package com.openclassrooms.mediscreen.Projet9;

import com.openclassrooms.mediscreen.Projet9.controller.PatientController;
import com.openclassrooms.mediscreen.Projet9.model.Patient;
import com.openclassrooms.mediscreen.Projet9.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PatientControllerTest {

    private PatientController patientController;
    private PatientService patientService;

    private Model model;
    private BindingResult bindingResult;
    @BeforeEach
    public void setUp() {
        patientService = mock(PatientService.class);
        model = mock(Model.class);
        bindingResult = mock(BindingResult.class);
        patientController = new PatientController(patientService);
    }

    @Test
    public void testListPatient() {
        List<Patient> patients = Arrays.asList(
                new Patient(1, "John", "Doe"),
                new Patient(2, "Jane", "Smith")
        );

        Mockito.when(patientService.findAll()).thenReturn(patients);

        List<Patient> result = patientController.listPatient();

        assertEquals(patients, result);
    }

    @Test
    public void testFindPatientById() {
        int patientId = 1;
        Patient patient = new Patient(patientId, "John", "Doe");

        when(patientService.getById(patientId)).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientController.findPatientById(patientId);

        assertEquals(Optional.of(patient), result);
    }

    @Test
    public void testAddPatient() {
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");

        String result = patientController.addPatient(patient, null, null);
        assertEquals("redirect:/patient/list", result);
    }
    @Test
    public void testAddPatientForm() {
        String result = patientController.addPatientForm();

        assertEquals("patient/add", result);
    }
    @Test
    public void testShowUpdateForm() {
        int patientId = 1;
        Patient patient = new Patient(patientId, "John", "Doe");

        when(patientService.getById(patientId)).thenReturn(Optional.of(patient));

        Model model = mock(Model.class);

        String result = patientController.showUpdateForm(patientId, model);

        verify(model).addAttribute("patient", patient);

        assertEquals("patient/update", result);
    }


    @Test
    public void testUpdateUserSuccess() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = patientController.updateUser(1, patient, bindingResult, null);

        assertEquals("patient/update", result);
    }

    @Test
    public void testDeletePatient() {
        int patientId = 1;
        patientController.deletePatient(patientId);
        verify(patientService).delete(patientId);
    }

}
