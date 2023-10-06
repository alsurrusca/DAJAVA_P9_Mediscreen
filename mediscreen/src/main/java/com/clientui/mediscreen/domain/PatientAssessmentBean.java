package com.clientui.mediscreen.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PatientAssessmentBean {

    PatientBeans patientBeans;
    private List<NotesBean> notes;
    private Risk risk;
    private int age;
    private int triggerCount;
    private List<String> triggerTerms = Arrays.asList("hemoglobin a1c", "microalbumin", "body height", "body weight", "smoker", "abnormal", "cholesterol", "dizziness", "relapse", "reaction", "antibodies");

    public PatientAssessmentBean(PatientBeans patientBeans, List<NotesBean> notes) {
        this.patientBeans = patientBeans;
        this.notes = notes;
    }

    public PatientAssessmentBean(){}

    public PatientBeans getPatientBeans() {
        return patientBeans;
    }

    public void setPatientBeans(PatientBeans patientBeans) {
        this.patientBeans = patientBeans;
    }

    public List<NotesBean> getNotes() {
        return notes;
    }

    public void setNotes(List<NotesBean> notes) {
        this.notes = notes;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int calculateTriggerCount(List<NotesBean> notes) {
        List<String> triggerTerms = Arrays.asList("hemoglobin a1c", "microalbumin", "body height", "body weight", "smoker", "abnormal", "cholesterol", "dizziness", "relapse", "reaction", "antibodies");
        int triggerCount = 0;

        if (notes != null) {
            for (NotesBean note : notes) {
                String noteContent = note.getNoteContent();


                if (noteContent != null) {
                    for (String term : triggerTerms) {
                        if (noteContent.toLowerCase().contains(term.toLowerCase())) {
                            triggerCount++;

                        }
                    }
                }
            }
        }
        return triggerCount;
    }

    public String riskString() {
        if (risk == risk.BORDERLINE) {
            return "Borderline";
        } else if (risk == risk.INDANGER) {
            return "In danger";
        } else if (risk == risk.EARLYONSET) {
            return "Early onset";
        } else {
            return "None";
        }
    }

    public String getRiskString(){
        return riskString();
    }

    public void setRiskString(Risk risk){
        this.risk = risk;
    }

    public List<String> getTriggerTerms() {
        return triggerTerms;
    }

    public void setTriggerTerms(List<String> triggerTerms) {
        this.triggerTerms = triggerTerms;
    }

    public int getTriggerCount(){
        return calculateTriggerCount( notes);
    }
    public void setTriggerCount(int triggerCount) {
        this.triggerCount = triggerCount;
    }


}
