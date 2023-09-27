package com.openclassrooms.mediscreen.assessments.service;

import com.openclassrooms.mediscreen.assessments.domain.Patient;
import com.openclassrooms.mediscreen.assessments.domain.PatientAssessment;
import com.openclassrooms.mediscreen.assessments.domain.PatientNote;
import com.openclassrooms.mediscreen.assessments.domain.Risk;
import com.openclassrooms.mediscreen.assessments.exceptions.NotesNotFoundException;
import com.openclassrooms.mediscreen.assessments.exceptions.PatientNotFoundException;
import com.openclassrooms.mediscreen.assessments.repository.PatientNoteRepository;
import com.openclassrooms.mediscreen.assessments.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@ComponentScan("com.openclassrooms.mediscreen.assessments.repository")
public class PatientAssessmentsService {

    private final PatientNoteRepository patientNoteRepository;
    private final PatientRepository patientRepository;

    // Variable is initialized from application.properties in prod, variable is defined here for testing purposes
    // This is to ensure that trigger terms are always constant for comparison in tests
    @Value("#{'${trigger.terms.array}'.split(',')}")
    private List<String> triggerTerms = Arrays.asList("hemoglobin a1c", "microalbumin", "body height",
            "body weight", "smoker", "abnormal", "cholesterol", "dizziness", "relapse", "reaction", "antibodies");

    private Logger log = LoggerFactory.getLogger(PatientAssessmentsService.class);

    public PatientAssessmentsService(PatientNoteRepository patientNoteRepository, PatientRepository patientRepository) {
        this.patientNoteRepository = patientNoteRepository;
        this.patientRepository = patientRepository;
    }

    public PatientAssessment patientAssessment(int patId) throws PatientNotFoundException, NotesNotFoundException {
        Patient patient = patientRepository.findPatientById(patId);
        if (patient == null) {
            throw new PatientNotFoundException("Patient with ID " + patId + " not found.");
        }

        List<PatientNote> notes = patientNoteRepository.findByPatientId(patId);
        if (notes.isEmpty()) {
            throw new NotesNotFoundException("Patient with ID " + patId + " has no notes.");
        }

        int triggerCount = countTrigger(notes);

        PatientAssessment patientAssessment = new PatientAssessment();
        patientAssessment.setPatient(patient);
        patientAssessment.setAge(calculateAge(patient.getDate()));
        calculateRisk(patientAssessment, triggerCount);

        return patientAssessment;
    }

    private int countTrigger(List<PatientNote> notes) {
        String noteString = "";
        for (PatientNote patientNote : notes) {
            noteString = noteString + " " + patientNote.getNote();
        }
        String finalNoteString = noteString;

        List<String> termsInNotes = new ArrayList<>();
        triggerTerms.forEach(term -> {
            if (finalNoteString.toLowerCase().contains(term.toLowerCase())) {
                termsInNotes.add(term);
            }
        });

        return termsInNotes.size();
    }

    private int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    private void calculateRisk(PatientAssessment patientAssessment, int triggerCount) {
        patientAssessment.setRisk(Risk.NONE);

        if (triggerCount >= 2 && patientAssessment.getAge() > 29) {
            patientAssessment.setRisk(Risk.BORDERLINE);
        }
        if (triggerCount >= 3 && patientAssessment.getAge() < 30 && patientAssessment.getPatient().getGender().equals("M")) {
            patientAssessment.setRisk(Risk.INDANGER);
        }
        if (triggerCount >= 4 && patientAssessment.getAge() < 30 && patientAssessment.getPatient().getGender().equals("F")) {
            patientAssessment.setRisk(Risk.INDANGER);
        }
        if (triggerCount >= 5 && patientAssessment.getAge() < 30 && patientAssessment.getPatient().getGender().equals("M")) {
            patientAssessment.setRisk(Risk.EARLYONSET);
        }
        if (triggerCount >= 7 && patientAssessment.getAge() < 30 && patientAssessment.getPatient().getGender().equals("F")) {
            patientAssessment.setRisk(Risk.EARLYONSET);
        }
        if (triggerCount >= 8 && patientAssessment.getAge() > 29) {
            patientAssessment.setRisk(Risk.EARLYONSET);
        }
    }
}
