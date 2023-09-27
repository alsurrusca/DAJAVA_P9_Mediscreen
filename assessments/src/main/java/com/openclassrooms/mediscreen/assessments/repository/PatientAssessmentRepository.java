package com.openclassrooms.mediscreen.assessments.repository;

import com.openclassrooms.mediscreen.assessments.domain.Patient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientAssessmentRepository  {


    Optional<Patient> findNotesByPatientId(int patientId);
}
