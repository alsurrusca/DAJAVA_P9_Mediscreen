package com.openclassrooms.mediscreen.assessments.controller;

import com.openclassrooms.mediscreen.assessments.domain.Patient;
import com.openclassrooms.mediscreen.assessments.domain.PatientAssessment;
import com.openclassrooms.mediscreen.assessments.domain.PatientNote;
import com.openclassrooms.mediscreen.assessments.service.PatientAssessmentsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientAssessmentController {

    private final PatientAssessmentsService patientAssessmentsService;


    public PatientAssessmentController(PatientAssessmentsService patientAssessmentsService) {
        this.patientAssessmentsService = patientAssessmentsService;
    }

    @GetMapping("/view/{patId}")
    public String getPatientAssessment(@PathVariable("patId") int patId, Patient patient, PatientNote note) {
        return "view";
    }
}









