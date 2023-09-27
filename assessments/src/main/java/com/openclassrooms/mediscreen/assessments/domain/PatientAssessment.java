package com.openclassrooms.mediscreen.assessments.domain;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

public class PatientAssessment {


    private List<PatientNote> note;
    private Risk risk;
    private int age;
    private Patient patient;

    public PatientAssessment(List<PatientNote> note, Patient patient) {
        this.note = note;
        this.patient = patient;
        age = getAge();
    }

    public PatientAssessment() {

    }

    public List<PatientNote> getNote() {
        return note;
    }

    public void setNote(List<PatientNote> note) {
        this.note = note;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String riskString(){
        if (risk.equals(risk.BORDERLINE)) {
            return "Borderline";
        }
        else if (risk.equals(risk.INDANGER)) {
            return "In danger";
        }
        else if (risk.equals(risk.EARLYONSET)) {
            return "Early onset";
        }
        else {
            return "None";
        }
    }

    public int getAge() {
        if (patient == null || patient.getDate() == null) {
            return 0;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = patient.getDate();
        return Period.between(birthDate, currentDate).getYears();
    }

    @Override
    public String toString() {
        return "Patient : " +
                " " + patient.getFirstName() + " " + patient.getLastName()
                + "(age " + getAge() + ") diabetes assessment is: " + riskString();
    }
}