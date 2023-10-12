package com.openclassrooms.mediscreen.assessments;

import com.openclassrooms.mediscreen.assessments.domain.Patient;
import com.openclassrooms.mediscreen.assessments.domain.PatientAssessment;
import com.openclassrooms.mediscreen.assessments.domain.PatientNote;
import com.openclassrooms.mediscreen.assessments.domain.Risk;
import com.openclassrooms.mediscreen.assessments.exceptions.NotesNotFoundException;
import com.openclassrooms.mediscreen.assessments.exceptions.PatientNotFoundException;
import com.openclassrooms.mediscreen.assessments.repository.PatientNoteRepository;
import com.openclassrooms.mediscreen.assessments.repository.PatientRepository;
import com.openclassrooms.mediscreen.assessments.service.PatientAssessmentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AssessmentsServiceTest {


    @Test
    public void testCalculateTriggerCount() {
        // Créer une instance du service
        PatientAssessmentsService service = new PatientAssessmentsService();

        // Créer des notes simulées
        PatientNote note1 = new PatientNote();
        note1.setNoteContent("hemoglobin a1c test");
        PatientNote note2 = new PatientNote();
        note2.setNoteContent("smoker");

        // Créer une liste de notes simulées
        List<PatientNote> notes = Arrays.asList(note1, note2);

        // Appeler la méthode à tester
        int triggerCount = service.calculateTriggerCount(notes);

        // Vérifier que le résultat est correct
        assertEquals(2, triggerCount); // Il y a deux termes déclencheurs dans les notes
    }

    @Test
    public void testCalculateAge() {
        // Créer une instance du service
        PatientAssessmentsService service = new PatientAssessmentsService();

        // Créer un patient simulé avec une date de naissance
        Patient patient = new Patient();
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        patient.setBirthDate(birthDate);

        // Appeler la méthode à tester
        int age = service.calculateAge(patient);

        // Vérifier que l'âge est correct
        int currentYear = LocalDate.now().getYear();
        assertEquals(currentYear - 1990, age);
    }

    @Test
    public void testCalculateRisk() {
        PatientAssessmentsService service = new PatientAssessmentsService();
        Patient patient = new Patient();
        patient.setGender("M");
        LocalDate birthDate = LocalDate.of(1995, 7, 10);
        patient.setBirthDate(birthDate);
        PatientAssessment assessmentBean = new PatientAssessment();
        assessmentBean.setPatient(patient);
        assessmentBean.setTriggerCount(6);
        service.calculateRisk(assessmentBean);

        assertEquals(Risk.EARLYONSET, assessmentBean.getRisk());
    }

}

