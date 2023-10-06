package com.openclassrooms.mediscreen.assessments;

import com.openclassrooms.mediscreen.assessments.domain.Patient;
import com.openclassrooms.mediscreen.assessments.domain.PatientNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainTest {

    private Patient patient;
    private PatientNote patientNote;

    @BeforeEach
    public void setUp() {
        patientNote = new PatientNote(1, "Sample note");
        patient = new Patient();
    }


    @Test
    public void testToString() {
        int patientId = 1;
        String lastName = "Doe";
        String firstName = "John";
        LocalDate date = LocalDate.of(1990, 1, 1);
        String gender = "Male";
        String address = "123 Main St";
        String phone = "555-1234";

        patient.setPatientId(patientId);
        patient.setLastName(lastName);
        patient.setFirstName(firstName);
        patient.setDate(date);
        patient.setGender(gender);
        patient.setAddress(address);
        patient.setPhone(phone);

        String expectedToString = "Patient{" +
                "patientId=" + patientId +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", date=" + date +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';

        assertEquals(expectedToString, patient.toString(), "toString should return the expected value");
    }

    @Test
    public void testGettersAndSetters() {

        int expectedId = 1;
        patientNote.setId(expectedId);
        assertEquals(expectedId, patientNote.getId(), "getId should return the expected value");

        int expectedPatientId = 2;
        patientNote.setPatientId(expectedPatientId);
        assertEquals(expectedPatientId, patientNote.getPatientId(), "getPatientId should return the expected value");

        String expectedNote = "Updated note";
        patientNote.setNote(expectedNote);
        assertEquals(expectedNote, patientNote.getNote(), "getNote should return the expected value");
    }

    @Test
    public void testConstructors() {

        int expectedId = 1;
        int expectedPatientId = 2;
        String expectedNote = "Sample note";

        PatientNote patientNoteWithId = new PatientNote(expectedId, expectedPatientId, expectedNote);
        assertEquals(expectedId, patientNoteWithId.getId(), "Constructor with id should set the id");
        assertEquals(expectedPatientId, patientNoteWithId.getPatientId(), "Constructor with id should set the patientId");
        assertEquals(expectedNote, patientNoteWithId.getNote(), "Constructor with id should set the note");

        PatientNote patientNoteWithoutId = new PatientNote(expectedPatientId, expectedNote);
        assertEquals(0, patientNoteWithoutId.getId(), "Constructor without id should set the id to 0");
        assertEquals(expectedPatientId, patientNoteWithoutId.getPatientId(), "Constructor without id should set the patientId");
        assertEquals(expectedNote, patientNoteWithoutId.getNote(), "Constructor without id should set the note");
    }
}

