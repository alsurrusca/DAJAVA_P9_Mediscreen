package com.openclassrooms.mediscreen.assessments.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.mediscreen.assessments.domain.PatientNote;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-notes", url="localhost:8082")
public interface PatientNoteRepository {

    @GetMapping("/notes/list")
    List<PatientNote> getAllNotes();

    @GetMapping("/notes/patient/{patientId}")
    List<PatientNote> getNoteByPatId(@PathVariable("patientId") int patientId);

}
