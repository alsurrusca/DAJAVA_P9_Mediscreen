package com.openclassrooms.mediscreen.assessments.controller;
import com.openclassrooms.mediscreen.assessments.domain.Risk;
import com.openclassrooms.mediscreen.assessments.repository.PatientNoteRepository;
import com.openclassrooms.mediscreen.assessments.repository.PatientRepository;
import com.openclassrooms.mediscreen.assessments.service.PatientAssessmentsService;
import org.springframework.web.bind.annotation.*;


@RestController
public class PatientAssessmentController {

    private final PatientAssessmentsService patientAssessmentsService;



    public PatientAssessmentController(PatientAssessmentsService patientAssessmentsService) {
        this.patientAssessmentsService = patientAssessmentsService;

    }

    @RequestMapping(method=RequestMethod.GET, value = "/view/{patId}")
    public Risk getCalculateRisk(@PathVariable("patId") int patId) {

        return patientAssessmentsService.patientAssessment(patId);
    }


}









