package com.openclassrooms.mediscreen.assessments.exceptions;

import com.openclassrooms.mediscreen.assessments.domain.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String message) {
        super(message);
    }

    public PatientNotFoundException(int patId) {
        super("Patient with ID " + patId + " not found");
    }
}
