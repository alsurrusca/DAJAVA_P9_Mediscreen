package com.openclassrooms.mediscreen.Projet9;
import com.openclassrooms.mediscreen.Projet9.model.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    @Test
    public void testPatientToString() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setBirthDate(LocalDate.of(1990, 5, 15));
        patient.setGender("Male");
        patient.setAddress("123 Main St");
        patient.setPhoneNumber("1234567890");

        String expectedToString = "Patient{id=1, firstName='John', lastName='Doe', birthDate=1990-05-15, gender='Male', address='123 Main St', phoneNumber='1234567890'}";
        String actualToString = patient.toString();

        assertEquals(expectedToString, actualToString, "toString method should return the expected string representation");
    }

    @Test
    public void testPatientConstructor() {
        Patient patient = new Patient(2, "Alice", "Smith");

        assertEquals(2, patient.getId(), "ID should match");
        assertEquals("Alice", patient.getFirstName(), "First name should match");
        assertEquals("Smith", patient.getLastName(), "Last name should match");
    }

    @Test
    public void testPatientDefaultConstructor() {
        Patient patient = new Patient();

        assertEquals(0, patient.getId(), "ID should be initialized to 0");
        assertNull(patient.getFirstName(), "First name should be null");
        assertNull(patient.getLastName(), "Last name should be null");
    }
}
