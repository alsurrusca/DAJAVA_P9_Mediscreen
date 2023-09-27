package com.openclassrooms.notes.controller;


import com.openclassrooms.notes.model.Notes;
import com.openclassrooms.notes.service.NotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NotesController {

    Logger log = LoggerFactory.getLogger(NotesController.class);



    @Autowired
    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/notes/list")
    public List<Notes> getAllNotes(){
        log.info("Find all notes SUCCESS");
        return notesService.findAllNotes();
    }

    @GetMapping("/notes/{id}")
    public Optional<Notes> getNoteById(@PathVariable("id") String id) {
        return notesService.getNoteById(id);
    }


    @GetMapping("/notes/addNote")
    public String addNotesForm(){

        log.info("Add notes SUCCESS");
        return "notes/add";
    }

    @PostMapping("/notes/addNote")
    public String addNotes(@RequestBody Notes notes, BindingResult result, Model model){
        log.info("Validate Note SUCCESS");
        return notesService.validateNotes(notes,result,model);
    }

    @GetMapping("/notes/update/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model){
        log.info("Find GET endpoint for patient with id " + id);
        return notesService.showUpdateForm(id, model);
    }

    @PostMapping("/notes/update/{id}")
    public String updateNotes(@PathVariable("id") String id, Notes notes, BindingResult result, Model model){
        log.info("Update note SUCCESS");
        return notesService.updateNotesPatient(id, notes,result,model);
    }

    @GetMapping(value = "/notes/delete/{id}")
    public void deleteNotes(@PathVariable String id){
        log.info("Id : " + id);
        notesService.deleteNotes(id);
    }

    @GetMapping("/notes/patient/{patId}")
    List<Notes> getNoteByPatId(@PathVariable("patId") int patId){
        return notesService.getNoteByPatId(patId);
    }




}
