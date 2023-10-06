package com.clientui.mediscreen.controller;

import com.clientui.mediscreen.domain.NotesBean;
import com.clientui.mediscreen.domain.PatientBeans;
import com.clientui.mediscreen.repository.MsNotesProxy;
import com.clientui.mediscreen.repository.MsPatientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;


import javax.validation.Valid;
import java.util.List;

@Controller
public class PatientController {

    private final MsPatientProxy patientProxy;

    private final MsNotesProxy notesProxy;
    Logger log = LoggerFactory.getLogger(MsPatientProxy.class);


    public PatientController(MsPatientProxy patientProxy, MsNotesProxy notesProxy) {
        this.patientProxy = patientProxy;
        this.notesProxy = notesProxy;
    }

    @GetMapping("/patient/list")
    public List<PatientBeans> view(Model model) {
        List<PatientBeans> patients = patientProxy.listPatients();
        model.addAttribute("patients", patients);
        return patients;
    }

    @GetMapping("/patient/add")
    public String addPatient(Model model) {
        model.addAttribute("patient", new PatientBeans());
        return "patient/add";
    }


    @PostMapping("/patient/add")
    public String validatePatient(Model model,  PatientBeans patientBeans) {
        patientProxy.validatePatient(patientBeans);
        model.addAttribute("patient", patientProxy.listPatients());
        return "redirect:/patient/list";
    }


    @GetMapping("/patient/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
       patientProxy.showUpdateForm(id);
        patientProxy.listPatients();
        PatientBeans patientBeans = patientProxy.findPatientById(id);
        model.addAttribute("patient",patientBeans);
        return "patient/update";
    }

    @PostMapping("/patient/update/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid PatientBeans user,
                             BindingResult result, Model model) {


        if (result.hasErrors()) {
            log.error("Update user FAILED");
            return "patient/update";
        }

        user.setId(id);
        patientProxy.validatePatient(user);
        log.info("Update User SUCCESS");

        model.addAttribute("patient", patientProxy.listPatients());

        return "redirect:/patient/list";

    }

    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id")int id, Model model){
        PatientBeans patientToDelete = patientProxy.findPatientById(id);

        // Assurez-vous que le patient existe
        if (patientToDelete != null) {
            // Récupérez toutes les notes associées à ce patient
            List<NotesBean> notesToDelete = notesProxy.getNoteByPatId(id);

            // Supprimez les notes associées
            for (NotesBean note : notesToDelete) {
                notesProxy.deleteNotes(note.getId());
            }

            // Ensuite, supprimez le patient lui-même
            patientProxy.deletePatient(id);

            log.info("Delete Patient SUCCESS ");
        } else {
            log.info("Patient not found.");
        }

        model.addAttribute("patients", patientProxy.listPatients());
        return "redirect:/patient/list";
    }

}
