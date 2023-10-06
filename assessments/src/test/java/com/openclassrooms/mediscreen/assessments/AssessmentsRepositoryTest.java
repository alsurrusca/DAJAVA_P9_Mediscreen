package com.openclassrooms.mediscreen.assessments;

import com.openclassrooms.mediscreen.assessments.domain.PatientNote;
import com.openclassrooms.mediscreen.assessments.repository.PatientNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AssessmentsRepositoryTest {

    private PatientNoteRepository patientNoteRepository;

    @BeforeEach
    public void setUp() {
        patientNoteRepository = new PatientNoteRepository();

    }

    @Test
    public void testFindAll() {

        List<PatientNote> allNotes = patientNoteRepository.findAll();


        assertNotNull(allNotes, "findAll should not return null");
    }


    @Test
    public void testFindByIdWithNonExistingId() {
        int nonExistingId = 999;
        Optional<PatientNote> notFoundNote = patientNoteRepository.findById(nonExistingId);

        assertFalse(notFoundNote.isPresent(), "findById should return an empty Optional for non-existing ID");
    }

    @Test
    public void testFindByPatientId() {
        int patientId = 1;
        List<PatientNote> patientNotes = patientNoteRepository.findByPatientId(patientId);

        assertNotNull(patientNotes, "findByPatientId should not return null");
    }

    @Test
    public void testSaveNewNote() {
        PatientNote newNote = new PatientNote(1,1,"test");
        newNote.setPatientId(0);

        PatientNote savedNote = patientNoteRepository.save(newNote);

        assertSame(newNote, savedNote, "save should return the same note object");

        assertNotEquals(0, newNote.getPatientId(), "save should set a non-zero ID for a new note");
    }

    @Test
    public void testSaveUpdateNote() {
        int existingId = 1;
        PatientNote existingNote = new PatientNote(1,1,"test");

        existingNote.setPatientId(existingId);

        PatientNote savedNote = patientNoteRepository.save(existingNote);

        assertSame(existingNote, savedNote, "save should return the same note object for an existing note");


        assertEquals(existingId, existingNote.getPatientId(), "save should not change the ID for an existing note");
    }

    @Test
    public void testDeleteById() {
        int idToDelete = 1;
        patientNoteRepository.deleteById(idToDelete);
        List<PatientNote> allNotes = patientNoteRepository.findAll();
        assertFalse(allNotes.stream().anyMatch(note -> note.getPatientId() == idToDelete), "deleteById should remove the note with the specified ID");
    }
}
