package com.clientui.mediscreen.controller;


import com.clientui.mediscreen.domain.NotesBean;
import com.clientui.mediscreen.domain.PatientAssessmentBean;
import com.clientui.mediscreen.domain.PatientBeans;
import com.clientui.mediscreen.repository.MsNotesProxy;
import com.clientui.mediscreen.repository.MsPatientAssessmentProxy;
import com.clientui.mediscreen.repository.MsPatientProxy;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
public class NotesController {

    private final MsNotesProxy notesProxy;

    @Autowired
    MsPatientProxy patientProxy;

    @Autowired
    MsPatientAssessmentProxy patientAssessmentProxy;

    Logger log = LoggerFactory.getLogger(NotesController.class);


    public NotesController(@Autowired MsNotesProxy notesProxy, @Autowired MsPatientProxy patientProxy) {
        this.notesProxy = notesProxy;
        this.patientProxy = patientProxy;
    }

    /**
     * Get all patient's notes
     * @param - No parameters
     * @return a list of all notes from msNotes
     */
    @ApiOperation(value = "Get all patient's notes")
    @GetMapping("/notes/list")
    public List<NotesBean> view(Model model) {
        List<NotesBean> notes = notesProxy.getAllNotes();
        List<PatientBeans> patients = patientProxy.listPatients();

        for (NotesBean note : notes) {
            int patId = note.getPatId();

            for(PatientBeans patient : patients){
                if(patient.getId() == patId){
                    note.setPatientFullName(patient.getFirstName() + " " + patient.getLastName());
                    break;
                }
            }
        }

        model.addAttribute("notes", notes);
        model.addAttribute("patient", patients);
        return notes;
    }

    /**
     * Get all patient's notes
     * @param - No parameters
     * @return a list of all notes from msNotes
     */
    @GetMapping("/notes/listNotesById/{patId}")
    public String viewAllNotesPatient (@PathVariable("patId") int patId, Model model) {
        List<NotesBean> notes = notesProxy.getNoteByPatId(patId);
        PatientBeans patient = patientProxy.findPatientById(patId);


        model.addAttribute("notes", notes);
        model.addAttribute("patient", patient);

        return "notes/listNotesById";
    }

    @GetMapping("/notes/addNote")
    public String addNotes(@RequestParam("patientId") int patientId, Model model) {
        // Créez une nouvelle instance de NotesBean
        NotesBean notesBean = new NotesBean();

        // Définissez l'ID du patient pour la nouvelle note
        notesBean.setPatId(patientId);

        // Ajoutez la nouvelle note et la liste des patients au modèle
        model.addAttribute("notes", notesBean);
        model.addAttribute("patient", patientProxy.findPatientById(patientId));

        return "notes/add";
    }

    @PostMapping("/notes/addNote")
    public String validateNotes(@ModelAttribute("notes") NotesBean notesBean, Model model) {
        // Validez les données de la nouvelle note, par exemple, en utilisant des annotations de validation

        // Enregistrez la nouvelle note en associant correctement le patient
        notesBean.setPatId(notesBean.getPatId()); // Assurez-vous que vous avez un getter pour PatientId dans NotesBean
        notesProxy.validateNotes(notesBean);

        // Réaffichez la page de détail du patient après l'ajout de la note
        return "redirect:/view/" + notesBean.getPatId();
    }

    /**
     * Update notes
     * @param id - patient's id
     * @return template of note update
     */
    @GetMapping("/notes/update/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        notesProxy.showUpdateForm(id);
        notesProxy.getAllNotes();
        log.info("Find notes by id SUCCESS");
        NotesBean notesBean = notesProxy.getNoteById(id);
        model.addAttribute("notes", notesBean);
        model.addAttribute("patient",patientProxy.listPatients() );
        return "notes/update";
    }

    @PostMapping("/notes/update/{id}")
    public String updateNotes(@PathVariable("id") String id, @Valid NotesBean notesBean, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("Update Notes FAILED");
            return "notes/update";
        }

        notesBean.setId(id);
        notesProxy.validateNotes(notesBean);
        log.info("Update Notes SUCCESS");
        model.addAttribute("notes", notesProxy.getAllNotes());
        return "redirect:/view/" + notesBean.getPatId();
    }

    @GetMapping("/notes/delete/{id}")
    public String deleteNotes(@PathVariable("id") String id, HttpServletRequest request) {
        notesProxy.deleteNotes(id);

        String referer = request.getHeader("referer");

        return "redirect:" + referer;
    }

}


