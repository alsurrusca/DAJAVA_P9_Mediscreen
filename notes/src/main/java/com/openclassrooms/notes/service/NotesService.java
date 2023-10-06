package com.openclassrooms.notes.service;

import com.openclassrooms.notes.model.Notes;
import com.openclassrooms.notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    public List<Notes> findAllNotes() {
        return notesRepository.findAll();}


    public String validateNotes(@RequestBody Notes notes, BindingResult result, Model model){
        if(!result.hasErrors()){
            System.out.println("Error found : " + result.toString());
            notesRepository.save(notes);
            model.addAttribute("notes", notesRepository.findAll());
            return "redirect:/notes/list";
        }

        return "notes/add";
    }



    public String showUpdateForm(String id, Model model){
        Notes notes = notesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid notes id : " + id));
        model.addAttribute("notes", notes);
        return "notes/update";
    }

    public String updateNotesPatient(String id, Notes notes, BindingResult result, Model model){
        if(result.hasErrors()){
            return "notes/update";
        }

        notes.setId(id);
        notesRepository.save(notes);
        model.addAttribute("notes", notesRepository.findAll());
        return "redirect:/notes/list";
    }

    public void deleteNotes(String id){
        Notes notes = notesRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid id : " + id));
        notesRepository.delete(notes);
    }


    public Optional<Notes> getNoteById(String id) {
        return notesRepository.findById(id);
    }

    public List<Notes> getNoteByPatId(int patId) {
        return notesRepository.findByPatId(patId);
    }
}
