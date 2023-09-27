package com.openclassrooms.mediscreen.Projet9.dao;

import com.openclassrooms.mediscreen.Projet9.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDao extends JpaRepository<Patient,Integer> {


    Patient findById(int id);


}
