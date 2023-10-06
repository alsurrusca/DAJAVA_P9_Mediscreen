package com.clientui.mediscreen.repository;



import com.clientui.mediscreen.domain.PatientBeans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@FeignClient(name="ms-patient", url = "localhost:8081")
public interface MsPatientProxy {



    @GetMapping("/patient")
    List<PatientBeans> listPatients();

    @GetMapping("/patient/{id}")
    PatientBeans findPatientById(@PathVariable("id") int id);

    @GetMapping("/patient/add")
    String addPatient();

    @PostMapping("/patient/add")
    String validatePatient(PatientBeans patientBeans);

    @GetMapping("/patient/delete/{id}")
    void deletePatient(@PathVariable("id") int id);

    @GetMapping("/patient/update/{id}")
    String showUpdateForm(@PathVariable("id") int id);

    @PutMapping("/patient/update/{id}")
    String updatePatient(@PathVariable("id") int id);



}