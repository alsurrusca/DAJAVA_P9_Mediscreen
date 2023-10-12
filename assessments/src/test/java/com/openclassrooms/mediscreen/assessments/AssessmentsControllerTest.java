package com.openclassrooms.mediscreen.assessments;

import com.openclassrooms.mediscreen.assessments.controller.PatientAssessmentController;
import com.openclassrooms.mediscreen.assessments.domain.Patient;
import com.openclassrooms.mediscreen.assessments.domain.PatientAssessment;
import com.openclassrooms.mediscreen.assessments.domain.PatientNote;
import com.openclassrooms.mediscreen.assessments.domain.Risk;
import com.openclassrooms.mediscreen.assessments.service.PatientAssessmentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AssessmentsControllerTest {
    @InjectMocks
    private PatientAssessmentController patientAssessmentController;

    @Mock
    private PatientAssessmentsService patientAssessmentsService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientAssessmentController).build();
    }

    @Test
    public void testGetCalculateRisk() throws Exception {
        PatientAssessment patientAssessment = new PatientAssessment();
        when(patientAssessmentsService.patientAssessment(1)).thenReturn(patientAssessment.getRisk());

        mockMvc.perform(MockMvcRequestBuilders.get("/view/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}