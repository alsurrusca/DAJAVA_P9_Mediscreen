package com.clientui.mediscreen.domain;



public class NotesBean {

    private String id;
    private int patId;
    private String risk;
    private String noteContent;
    private String patientFullName;
    private PatientAssessmentBean patientAssessmentBean;



    public NotesBean() {
    }

    public PatientAssessmentBean getPatientAssessmentBean() {
        return patientAssessmentBean;
    }

    public void setPatientAssessmentBean(PatientAssessmentBean patientAssessmentBean) {
        this.patientAssessmentBean = patientAssessmentBean;
    }

    public String getPatientFullName() {
        return patientFullName;
    }

    public void setPatientFullName(String patientFullName) {
        this.patientFullName = patientFullName;
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

    public String getNoteText(){
        return noteContent;
    }
}
