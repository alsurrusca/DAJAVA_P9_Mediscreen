package com.openclassrooms.mediscreen.Projet9.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientIntrouvableException extends RuntimeException {

    public  PatientIntrouvableException(String s) {
        super(s);
    }
}
