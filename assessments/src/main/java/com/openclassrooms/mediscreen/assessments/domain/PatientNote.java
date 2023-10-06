package com.openclassrooms.mediscreen.assessments.domain;

public class PatientNote {
    private int id;
    private int patientId;
    private String note;

    public PatientNote(int id, int patientId, String note) {
        this.id = id;
        this.patientId = patientId;
        this.note = note;
    }

    public PatientNote(int patientId,String note) {
        this.patientId = patientId;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
