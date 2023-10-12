package com.openclassrooms.mediscreen.assessments.domain;

import java.util.Arrays;
import java.util.List;

public class PatientAssessment {

    Patient patientBeans;
    private List<PatientNote> notes;
    private Risk risk;
    private int age;
    private int triggerCount;
    private List<String> triggerTerms = Arrays.asList("hemoglobin a1c", "microalbumin", "body height", "body weight", "smoker", "abnormal", "cholesterol", "dizziness", "relapse", "reaction", "antibodies");

    public PatientAssessment(Patient patientBeans, List<PatientNote> patientNotes) {
        this.patientBeans = patientBeans;
        this.notes = notes;
    }

    public PatientAssessment(){}

    public Patient getPatient() {
        return patientBeans;
    }

    public void setPatient(Patient patientBeans) {
        this.patientBeans = patientBeans;
    }

    public List<PatientNote> getNotes() {
        return notes;
    }

    public void setNotes(List<PatientNote> notes) {
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


    public List<String> getTriggerTerms() {
        return triggerTerms;
    }

    public void setTriggerTerms(List<String> triggerTerms) {
        this.triggerTerms = triggerTerms;
    }

    public int getTriggerCount(){
        return triggerCount;
    }
    public void setTriggerCount(int triggerCount) {
        this.triggerCount = triggerCount;
    }


}
