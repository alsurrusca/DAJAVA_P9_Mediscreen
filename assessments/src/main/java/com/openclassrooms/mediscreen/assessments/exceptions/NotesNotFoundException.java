package com.openclassrooms.mediscreen.assessments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NotesNotFoundException extends RuntimeException {


    public NotesNotFoundException(String message) {
        super(message);
    }

    public NotesNotFoundException(int patId) {
        super("Notes not found for patient with ID " + patId);
    }
}

