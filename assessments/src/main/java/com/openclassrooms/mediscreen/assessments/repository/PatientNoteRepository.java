package com.openclassrooms.mediscreen.assessments.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.mediscreen.assessments.domain.PatientNote;
import org.springframework.stereotype.Repository;

@Repository
public class PatientNoteRepository {

    private static List<PatientNote> notes = new ArrayList<>();
    private static int nextId = 1;

    public List<PatientNote> findAll() {
        return notes;
    }

    public Optional<PatientNote> findById(int id) {
        return notes.stream().filter(note -> note.getPatientId() == id).findFirst();
    }

    public List<PatientNote> findByPatientId(int patientId) {
        List<PatientNote> patientNotes = new ArrayList<>();
        for (PatientNote note : notes) {
            if (note.getId() == patientId) {
                patientNotes.add(note);
            }
        }
        return patientNotes;
    }

    public PatientNote save(PatientNote note) {
        if (note.getPatientId() == 0) {
            note.setPatientId(nextId++);
            notes.add(note);
        } else {
            int index = notes.indexOf(note);
            if (index != -1) {
                notes.set(index, note);
            } else {
                notes.add(note);
            }
        }
        return note;
    }

    public void deleteById(int id) {
        notes.removeIf(note -> note.getPatientId() == id);
    }

}
