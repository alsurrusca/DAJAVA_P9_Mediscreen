package com.openclassrooms.mediscreen.assessments;

import com.openclassrooms.mediscreen.assessments.controller.PatientAssessmentController;
import com.openclassrooms.mediscreen.assessments.domain.Patient;
import com.openclassrooms.mediscreen.assessments.domain.PatientNote;
import com.openclassrooms.mediscreen.assessments.service.PatientAssessmentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AssessmentsControllerTest {
    @InjectMocks
    private PatientAssessmentController patientAssessmentController;

    @Mock
    private PatientAssessmentsService patientAssessmentsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPatientAssessment() {
        int patId = 1;
        Patient patient = new Patient();
        PatientNote note = new PatientNote(1,1,"test");
        Model mockModel = mock(Model.class);


        String result = patientAssessmentController.getPatientAssessment(patId, patient, note);

        assertEquals("view", result, "getPatientAssessment should return 'view'");

    }
}