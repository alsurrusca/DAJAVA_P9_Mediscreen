package com.clientui.mediscreen.controller;

import com.clientui.mediscreen.domain.NotesBean;
import com.clientui.mediscreen.domain.PatientAssessmentBean;
import com.clientui.mediscreen.domain.PatientBeans;
import com.clientui.mediscreen.domain.Risk;
import com.clientui.mediscreen.repository.MsNotesProxy;
import com.clientui.mediscreen.repository.MsPatientAssessmentProxy;
import com.clientui.mediscreen.repository.MsPatientProxy;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    public String getPatientAssessment(@PathVariable("patId") int patId, Model model) {
        PatientBeans patient = patientProxy.findPatientById(patId);
        List<NotesBean> notes = notesProxy.getNoteByPatId(patId);

        PatientAssessmentBean assessmentBean = new PatientAssessmentBean(patient, notes);

        //Calcul de l'âge

        LocalDate currentDate = LocalDate.now();
        java.sql.Date birthDate = patient.getBirthDate();
        LocalDate localDate = birthDate.toLocalDate();
        int age = Period.between(localDate, currentDate).getYears();
        assessmentBean.setAge(age);

        int triggerCount = assessmentBean.getTriggerCount();

        assessmentBean.setTriggerCount(triggerCount);
        calculateRisk(assessmentBean);

        model.addAttribute("assessment", assessmentBean);
        model.addAttribute("patient", patient);
        return "view";
    }

    /**
     * Calculate risk with trigger count
     * @param assessmentBean
     */
    private void calculateRisk(PatientAssessmentBean assessmentBean) {
        int triggerCount = assessmentBean.getTriggerCount();
        int age = assessmentBean.getAge();
        Risk risk = Risk.NONE;
        if (triggerCount >= 2 && age > 29) {
            risk = Risk.BORDERLINE;
        }
        if (triggerCount >= 3 && age < 30 && assessmentBean.getPatientBeans().getGender().equals("M")) {
            risk = Risk.INDANGER;
        }
        if (triggerCount >= 4 && age < 30 && assessmentBean.getPatientBeans().getGender().equals("F")) {
            risk = Risk.INDANGER;
        }
        if (triggerCount >= 5 && age < 30 && assessmentBean.getPatientBeans().getGender().equals("M")) {
            risk = Risk.EARLYONSET;
        }
        if (triggerCount >= 7 && age < 30 && assessmentBean.getPatientBeans().getGender().equals("F")) {
            risk = Risk.EARLYONSET;
        }
        if (triggerCount >= 8 && age > 29) {
            risk = Risk.EARLYONSET;
        }
        assessmentBean.setRisk(risk);
    }

}
