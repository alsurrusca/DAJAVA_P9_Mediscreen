package com.clientui.mediscreen.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NotesNotFoundException extends RuntimeException {


    public NotesNotFoundException(String message) {
        super(message);
    }

    public NotesNotFoundException(int patientId) {
        super("Notes not found for patient with ID " + patientId);
    }
}

