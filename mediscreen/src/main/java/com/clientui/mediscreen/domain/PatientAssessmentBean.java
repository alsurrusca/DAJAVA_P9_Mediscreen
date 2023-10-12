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

    
}
