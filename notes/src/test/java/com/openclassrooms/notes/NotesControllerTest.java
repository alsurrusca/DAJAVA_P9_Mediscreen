package com.openclassrooms.notes;

import com.openclassrooms.notes.controller.NotesController;
import com.openclassrooms.notes.model.Notes;
import com.openclassrooms.notes.service.NotesService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class NotesControllerTest {

    @InjectMocks
    private NotesController notesController;

    @Mock
    private NotesService notesService;
    private Model model;
    private BindingResult bindingResult;
    @BeforeEach
    public void setUp() {
        notesService = mock(NotesService.class);
        model = mock(Model.class);
        bindingResult = mock(BindingResult.class);
        notesController = new NotesController(notesService);
    }


    @Test
    public void testGetAllNotes() {
        List<Notes> sampleNotesList = new ArrayList<>();
        sampleNotesList.add(new Notes());
        sampleNotesList.add(new Notes());

       when(notesService.findAllNotes()).thenReturn(sampleNotesList);

        List<Notes> resultNotesList = notesController.getAllNotes();

        assertEquals(sampleNotesList, resultNotesList, "getAllNotes should return the expected list of Notes");

        verify(notesService, times(1)).findAllNotes();
    }

    @Test
    public void testGetNoteByIdWithValidId() {
        String validNotesId = "123";

        Notes sampleNotes = new Notes();
        when(notesService.getNoteById(validNotesId)).thenReturn(Optional.of(sampleNotes));

        Optional<Notes> result = notesController.getNoteById(validNotesId);

        assertTrue(result.isPresent(), "getNoteById should return non-empty Optional");
        assertEquals(sampleNotes, result.get(), "getNoteById should return the expected Notes object");

        verify(notesService, times(1)).getNoteById(validNotesId);
    }

    @Test
    public void testGetNoteByIdWithInvalidId() {
        String invalidNotesId = "999";

        when(notesService.getNoteById(invalidNotesId)).thenReturn(Optional.empty());

        Optional<Notes> result = notesController.getNoteById(invalidNotesId);

        assertEquals(Optional.empty(), result, "getNoteById should return an empty Optional");

        verify(notesService, times(1)).getNoteById(invalidNotesId);
    }

    @Test
    public void testAddNotesForm() {
        String result = notesController.addNotesForm();

        assertEquals("notes/add", result, "addNotesForm should return 'notes/add'");
    }

    @Test
    public void testAddNotes() {
        Notes sampleNotes = new Notes();
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);

        Model mockModel = mock(Model.class);

        when(notesService.validateNotes(sampleNotes, mockBindingResult, mockModel)).thenReturn("redirect:/notes/list");

        String result = notesController.addNotes(sampleNotes, mockBindingResult, mockModel);

        assertEquals("redirect:/notes/list", result, "addNotes should return 'redirect:/notes/list'");

        verify(notesService, times(1)).validateNotes(sampleNotes, mockBindingResult, mockModel);
    }

    @Test
    public void testShowUpdateFormWithValidId() {
        String validNotesId = "123";

        Model mockModel = mock(Model.class);

        when(notesService.showUpdateForm(validNotesId, mockModel)).thenReturn("notes/update");

        String result = notesController.showUpdateForm(validNotesId, mockModel);

        assertEquals("notes/update", result, "showUpdateForm should return 'notes/update'");

        verify(notesService, times(1)).showUpdateForm(validNotesId, mockModel);
    }


    @Test
    public void testUpdateNotesWithoutErrors() {
        String validNotesId = "123";
        Notes sampleNotes = new Notes();
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);

        Model mockModel = mock(Model.class);

        when(notesService.updateNotesPatient(validNotesId, sampleNotes, mockBindingResult, mockModel)).thenReturn("redirect:/notes/list");

        String result = notesController.updateNotes(validNotesId, sampleNotes, mockBindingResult, mockModel);

        assertEquals("redirect:/notes/list", result, "updateNotes should return 'redirect:/notes/list'");

        verify(notesService, times(1)).updateNotesPatient(validNotesId, sampleNotes, mockBindingResult, mockModel);
    }

    @Test
    public void testDeleteNotesWithValidId() {
        String validNotesId = "123";

        notesController.deleteNotes(validNotesId);

        verify(notesService, times(1)).deleteNotes(validNotesId);
    }




}
