package com.clientui.mediscreen.controller;

import com.clientui.mediscreen.domain.NotesBean;
import com.clientui.mediscreen.domain.PatientAssessmentBean;
import com.clientui.mediscreen.domain.PatientBeans;
import com.clientui.mediscreen.domain.Risk;
import com.clientui.mediscreen.repository.MsNotesProxy;
import com.clientui.mediscreen.repository.MsPatientAssessmentProxy;
import com.clientui.mediscreen.repository.MsPatientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Controller
public class PatientAssessmentController {

    private final MsPatientAssessmentProxy patientAssessmentProxy;
    private final PatientAssessmentBean patientAssessmentBean;

    private Logger log = LoggerFactory.getLogger(PatientAssessmentController.class);

    private final MsPatientProxy patientProxy;

    private final MsNotesProxy notesProxy;

    public PatientAssessmentController(MsPatientAssessmentProxy patientAssessmentProxy, MsPatientProxy patientProxy, MsNotesProxy notesProxy, PatientAssessmentBean patientAssessmentBean) {
        this.patientAssessmentProxy = patientAssessmentProxy;
        this.patientProxy = patientProxy;
        this.notesProxy = notesProxy;
        this.patientAssessmentBean = patientAssessmentBean;
    }

    /**
     * get patient assessment with age
     * @param patId
     * @param model
     * @return template view
     */
    @GetMapping("/view/{patId}")
    public String getCalculateRisk(@PathVariable("patId") int patId, Model model) {
        PatientBeans patient = patientProxy.findPatientById(patId);
        List<NotesBean> notes = notesProxy.getNoteByPatId(patId);

        PatientAssessmentBean assessmentBean = new PatientAssessmentBean(patient, notes);


        LocalDate currentDate = LocalDate.now();
        java.sql.Date birthDate = Date.valueOf(patient.getBirthDate());
        LocalDate localDate = birthDate.toLocalDate();
        int age = Period.between(localDate, currentDate).getYears();
        assessmentBean.setAge(age);



        model.addAttribute("assessment", assessmentBean);
        model.addAttribute("patient", patient);
        model.addAttribute("risk",patientAssessmentProxy.getCalculateRisk(patId));
        return "view";
    }



}
