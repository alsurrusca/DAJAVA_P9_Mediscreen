package com.openclassrooms.mediscreen.assessments.repository;

import com.openclassrooms.mediscreen.assessments.domain.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PatientRepository {

    Patient patients;

    public Patient findPatientById(int id) {
        return patients.getPatientId(id);
    }


}

