package com.clientui.mediscreen.repository;


import com.clientui.mediscreen.domain.NotesBean;
import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-notes", url = "localhost:8082")
public interface MsNotesProxy {

    @GetMapping("/notes/list")
    List<NotesBean> getAllNotes();

    @GetMapping("/notes/{id}")
    NotesBean getNoteById(@PathVariable("id") String noteId);

    @GetMapping("/notes/patient/{patId}")
    List<NotesBean> getNoteByPatId(@PathVariable("patId") int patientId);

    @GetMapping("/notes/addNote")
    String addNotes();

    @PostMapping("/notes/addNote")
    String validateNotes(NotesBean notesBean);

    @GetMapping("/notes/update/{id}")
    String showUpdateForm(@PathVariable("id") String id);

    @PutMapping("/notes/update/{id}")
    void updateNotes(@PathVariable("id") String id, @RequestBody NotesBean note);


    @GetMapping("/notes/delete/{id}")
    void deleteNotes(@PathVariable("id") String id);



}