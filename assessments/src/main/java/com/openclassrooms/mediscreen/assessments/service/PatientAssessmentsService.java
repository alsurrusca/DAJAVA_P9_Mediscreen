package com.openclassrooms.mediscreen.assessments.service;

import com.openclassrooms.mediscreen.assessments.domain.Patient;
import com.openclassrooms.mediscreen.assessments.domain.PatientAssessment;
import com.openclassrooms.mediscreen.assessments.domain.PatientNote;
import com.openclassrooms.mediscreen.assessments.domain.Risk;
import com.openclassrooms.mediscreen.assessments.exceptions.NotesNotFoundException;
import com.openclassrooms.mediscreen.assessments.exceptions.PatientNotFoundException;
import com.openclassrooms.mediscreen.assessments.repository.PatientNoteRepository;
import com.openclassrooms.mediscreen.assessments.repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PatientAssessmentsService {

    @Autowired
    private PatientNoteRepository patientNoteRepository;
    @Autowired
    private PatientRepository patientRepository;

    // Variable is initialized from application.properties in prod, variable is defined here for testing purposes
    // This is to ensure that trigger terms are always constant for comparison in tests
    @Value("#{'${trigger.terms.array}'.split(',')}")
    private List<String> triggerTerms = Arrays.asList("hemoglobin a1c", "microalbumin", "body height",
            "body weight", "smoker", "abnormal", "cholesterol", "dizziness", "relapse", "reaction", "antibodies");


    public Risk patientAssessment(int patId) throws PatientNotFoundException, NotesNotFoundException {

        Patient patient = patientRepository.findPatientById(patId);
        List<PatientNote> patientNotes = patientNoteRepository.getNoteByPatId(patId);

        PatientAssessment patientAssessment = new PatientAssessment(patient, patientNotes);
        patientAssessment.setAge(calculateAge(patientAssessment.getPatient()));
        patientAssessment.setNotes(patientNotes);
        int triggerCount = calculateTriggerCount(patientNotes);
        patientAssessment.setTriggerCount(triggerCount);
        calculateRisk(patientAssessment);
        return patientAssessment.getRisk();
    }

    public int calculateTriggerCount(List<PatientNote> notes) {
        List<String> triggerTerms = Arrays.asList("hemoglobin a1c", "microalbumin", "body height", "body weight", "smoker", "abnormal", "cholesterol", "dizziness", "relapse", "reaction", "antibodies");
        int triggerCount = 0;
        if (notes != null) {
            for (PatientNote note : notes) {
                String noteContent = note.getNoteContent();

                if (noteContent != null) {
                    for (String term : triggerTerms) {
                        if (noteContent.toLowerCase().contains(term.toLowerCase())) {
                            triggerCount++;

                        }
                    }
                }
            }
        }
        return triggerCount;
    }

    public int calculateAge(Patient patient) {

        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = patient.getBirthDate();
        int age = Period.between(birthDate, currentDate).getYears();
        return age;
    }

    public void calculateRisk(PatientAssessment assessmentBean) {
        int triggerCount = assessmentBean.getTriggerCount();
        int age = assessmentBean.getAge();
        Risk risk = Risk.NONE;
        if (triggerCount >= 2 && age > 29) {
            risk = Risk.BORDERLINE;
        }
        if (triggerCount >= 3 && age < 30 && assessmentBean.getPatient().getGender().equals("M")) {
            risk = Risk.INDANGER;
        }
        if (triggerCount >= 4 && age < 30 && assessmentBean.getPatient().getGender().equals("F")) {
            risk = Risk.INDANGER;
        }
        if (triggerCount >= 5 && age < 30 && assessmentBean.getPatient().getGender().equals("M")) {
            risk = Risk.EARLYONSET;
        }
        if (triggerCount >= 7 && age < 30 && assessmentBean.getPatient().getGender().equals("F")) {
            risk = Risk.EARLYONSET;
        }
        if (triggerCount >= 8 && age > 29) {
            risk = Risk.EARLYONSET;
        }
        assessmentBean.setRisk(risk);
    }


}
