package com.openclassrooms.mediscreen.Projet9.service;

import com.openclassrooms.mediscreen.Projet9.dao.PatientDao;
import com.openclassrooms.mediscreen.Projet9.model.Patient;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private PatientDao patientDao;

    public PatientService(PatientDao repository) {
        this.patientDao = repository;
    }

    public List<Patient> findAll() {
        return patientDao.findAll();
    }

    public Patient validate(@RequestBody Patient patient, BindingResult result, Model model) {
        return patientDao.save(patient);
    }


    public Optional<Patient> getById(Integer id) {
        return patientDao.findById(id);
    }

    public void delete(Integer id) {
        Patient patient = patientDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient id : " + id));
        patientDao.delete(patient);
    }



}


