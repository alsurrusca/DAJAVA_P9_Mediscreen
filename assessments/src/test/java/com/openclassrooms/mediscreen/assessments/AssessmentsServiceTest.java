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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AssessmentsServiceTest {

    @InjectMocks
    private PatientAssessmentsService patientAssessmentsService;

    @Mock
    private PatientNoteRepository patientNoteRepository;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPatientAssessment() throws PatientNotFoundException, NotesNotFoundException {
        // Créez des données simulées pour le test
        int patId = 1;
        Patient patient = new Patient();
        patient.setPatientId(patId);
        patient.setDate(LocalDate.of(1980, 1, 1));

        List<PatientNote> notes = new ArrayList<>();
        notes.add(new PatientNote(1,1,"Note with abnormal cholesterol"));
        notes.add(new PatientNote(1,1,"Note with dizziness"));

        // Configurez le comportement simulé des repositories
        when(patientRepository.findPatientById(patId)).thenReturn(patient);
        when(patientNoteRepository.findByPatientId(patId)).thenReturn(notes);

        // Appelez la méthode du service pour obtenir l'évaluation du patient
        PatientAssessment assessment = patientAssessmentsService.patientAssessment(patId);

        // Vérifiez que l'âge est calculé correctement
        assertEquals(43, assessment.getAge(), "Age should be calculated correctly");

        // Vérifiez que le risque est correctement calculé en fonction des notes
        assertEquals(Risk.BORDERLINE, assessment.getRisk(), "Risk should be BORDERLINE");

        // Vérifiez que les méthodes des repositories ont été appelées
        verify(patientRepository, times(1)).findPatientById(patId);
        verify(patientNoteRepository, times(1)).findByPatientId(patId);
    }

    @Test
    public void testPatientAssessmentWithNoPatient() {
        // Créez un cas où le patient n'est pas trouvé
        int patId = 1;

        // Configurez le comportement simulé du repository pour retourner null
        when(patientRepository.findPatientById(patId)).thenReturn(null);

        // Appelez la méthode du service en attendant une exception
        assertThrows(PatientNotFoundException.class, () -> {
            patientAssessmentsService.patientAssessment(patId);
        });

        // Vérifiez que le repository a été appelé
        verify(patientRepository, times(1)).findPatientById(patId);
    }

    @Test
    public void testPatientAssessmentWithNoNotes() {
        // Créez un cas où il n'y a pas de notes pour le patient
        int patId = 1;
        Patient patient = new Patient();
        patient.setPatientId(patId);

        // Configurez le comportement simulé des repositories
        when(patientRepository.findPatientById(patId)).thenReturn(patient);
        when(patientNoteRepository.findByPatientId(patId)).thenReturn(new ArrayList<>());

        // Appelez la méthode du service en attendant une exception
        assertThrows(NotesNotFoundException.class, () -> {
            patientAssessmentsService.patientAssessment(patId);
        });

        // Vérifiez que les repositories ont été appelés
        verify(patientRepository, times(1)).findPatientById(patId);
        verify(patientNoteRepository, times(1)).findByPatientId(patId);
    }
}

