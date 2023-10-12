package com.openclassrooms.mediscreen.assessments.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public class PatientNote {

    private String id;
    private int patId;
    private String risk;
    private String noteContent;

    public PatientNote() {
    }

    public PatientNote(String id, int patId, String noteContent) {
        this.id = id;
        this.patId = patId;
        this.noteContent = noteContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPatId() {
        return patId;
    }

    public void setPatId(int patId) {
        this.patId = patId;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
