package com.openclassrooms.mediscreen.assessments.repository;

import com.openclassrooms.mediscreen.assessments.domain.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ms-patient", url = "localhost:8081")
public interface PatientRepository {

    @GetMapping("/patient/{id}")
    Patient findPatientById(@PathVariable("id") int id);



}

