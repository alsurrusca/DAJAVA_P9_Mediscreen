package com.openclassrooms.mediscreen.Projet9.controller;

import com.openclassrooms.mediscreen.Projet9.model.Patient;
import com.openclassrooms.mediscreen.Projet9.service.PatientService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Api("API pour les opérations CRUD sur les patients")
@RestController
public class PatientController {

    @Autowired
    private final PatientService patientService;



    Logger log = LoggerFactory.getLogger(PatientController.class);

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient")
    public List<Patient> listPatient(){
        log.info("Find all patient success");
        return patientService.findAll();
    }

    @GetMapping("/patient/{id}")
    public Optional<Patient> findPatientById(@PathVariable("id") int id) {
        return patientService.getById(id);
    }

    @GetMapping("/patient/add")
    public String addPatientForm(){

        return "patient/add";
    }

    @PostMapping("/patient/add")
    public Patient addPatient(@RequestBody Patient patient, BindingResult result, Model model){
     log.info("validate SUCCESS");
          return patientService.validate(patient,result,model);
    }

    @GetMapping("/patient/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Patient user = patientService.getById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        log.info("Find user by id SUCCESS");
        model.addAttribute("patient", user);
        return "patient/update";
    }

    @PutMapping("/patient/update/{id}")
    public String updateUser(@PathVariable("id") int id, Patient patient,
                             BindingResult result, Model model) {
        if (!result.hasErrors()) {
            log.error("Update user FAILED");
            return "patient/update";
        }

        patientService.validate(patient, result, model);
        log.info("Update User SUCCESS");
        model.addAttribute("patient", patientService.findAll());
        log.info("test");
        return "redirect:/patient/list";
    }

    @GetMapping(value = "/patient/delete/{id}")
    public void deletePatient(@PathVariable int id){
        log.info("Id : " + id);
        patientService.delete(id);
    }


}
