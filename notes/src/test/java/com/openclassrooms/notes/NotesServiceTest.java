package com.openclassrooms.notes;

import com.openclassrooms.notes.model.Notes;
import com.openclassrooms.notes.repository.NotesRepository;
import com.openclassrooms.notes.service.NotesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotesServiceTest {

    @InjectMocks
    NotesService notesService;

    @Mock
    NotesRepository notesRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllNotes() {
        List<Notes> sampleNotesList = new ArrayList<>();
        sampleNotesList.add(new Notes());
        sampleNotesList.add(new Notes());

        when(notesRepository.findAll()).thenReturn(sampleNotesList);

        List<Notes> resultNotes = notesService.findAllNotes();

        assertEquals(sampleNotesList, resultNotes, "findAllNotes should return the expected list of notes");
    }

    @Test
    public void testValidateNotesWithNoErrors() {
        // Create a sample Notes object and BindingResult with no errors
        Notes sampleNotes = new Notes();
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);

        // Call the method from the service
        String result = notesService.validateNotes(sampleNotes, mockBindingResult, mock(Model.class));

        // Verify that the result is "redirect:/notes/list" since there are no errors
        assertEquals("redirect:/notes/list", result, "validateNotes should return 'redirect:/notes/list'");
        verify(notesRepository, times(1)).save(sampleNotes);
    }

    @Test
    public void testShowUpdateFormWithValidId() {
        // Créez un identifiant de notes valide et un objet Notes correspondant simulé
        String validNotesId = "123";
        Notes sampleNotes = new Notes();
        when(notesRepository.findById(validNotesId)).thenReturn(Optional.of(sampleNotes));

        // Créez un objet Model simulé
        Model mockModel = mock(Model.class);

        // Appelez la méthode de service
        String result = notesService.showUpdateForm(validNotesId, mockModel);

        // Vérifiez que la méthode a ajouté l'objet "notes" au modèle
        verify(mockModel, times(1)).addAttribute("notes", sampleNotes);

        // Vérifiez que la méthode retourne la vue attendue ("notes/update")
        assertEquals("notes/update", result, "showUpdateForm should return 'notes/update'");
    }

    @Test
    public void testShowUpdateFormWithInvalidId() {
        // Créez un identifiant de notes invalide
        String invalidNotesId = "999";

        // Faites en sorte que notesRepository.findById renvoie un Optional vide (identifiant invalide)
        when(notesRepository.findById(invalidNotesId)).thenReturn(Optional.empty());

        // Créez un objet Model simulé
        Model mockModel = mock(Model.class);

        // Appelez la méthode de service avec l'identifiant invalide
        // La méthode devrait lancer une exception IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            notesService.showUpdateForm(invalidNotesId, mockModel);
        });
    }

    @Test
    public void testUpdateNotesPatientWithErrors() {
        // Créez un objet Notes simulé et un BindingResult simulé avec des erreurs
        Notes sampleNotes = new Notes();
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(true);

        // Créez un objet Model simulé
        Model mockModel = mock(Model.class);

        // Appelez la méthode de service
        String result = notesService.updateNotesPatient("123", sampleNotes, mockBindingResult, mockModel);

        // Vérifiez que la méthode renvoie "notes/update" car il y a des erreurs
        assertEquals("notes/update", result, "updateNotesPatient should return 'notes/update'");
    }

    @Test
    public void testUpdateNotesPatientWithoutErrors() {
        // Créez un identifiant valide, un objet Notes simulé et un BindingResult simulé sans erreurs
        String validNotesId = "123";
        Notes sampleNotes = new Notes();

        // Créez une instance simulée de BindingResult
        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);

        // Utilisez Mockito.spy pour espionner la méthode hasErrors
        BindingResult spyBindingResult = Mockito.spy(mockBindingResult);
        Mockito.when(spyBindingResult.hasErrors()).thenReturn(false);

        // Créez un objet Model simulé
        Model mockModel = mock(Model.class);

        // Appelez la méthode de service
        String result = notesService.updateNotesPatient(validNotesId, sampleNotes, spyBindingResult, mockModel);

        // Vérifiez que la méthode a correctement défini l'ID des notes
        assertEquals(validNotesId, sampleNotes.getId(), "ID should be set correctly");

        // Vérifiez que la méthode a appelé notesRepository.save avec les notes simulées
        verify(notesRepository, times(1)).save(sampleNotes);

        // Vérifiez que la méthode a ajouté "notes" au modèle
        verify(mockModel, times(1)).addAttribute("notes", notesRepository.findAll());

        // Vérifiez que la méthode renvoie "redirect:/notes/list"
        assertEquals("redirect:/notes/list", result, "updateNotesPatient should return 'redirect:/notes/list'");
    }


    @Test
    public void testDeleteNotesWithValidId() {
        // Créez un identifiant de notes valide
        String validNotesId = "123";

        // Créez un objet Notes simulé
        Notes sampleNotes = new Notes();

        // Faites en sorte que notesRepository.findById renvoie l'objet simulé correspondant
        when(notesRepository.findById(validNotesId)).thenReturn(Optional.of(sampleNotes));

        // Appelez la méthode de service pour supprimer les notes
        notesService.deleteNotes(validNotesId);

        // Vérifiez que notesRepository.delete a été appelé avec les notes simulées
        verify(notesRepository, times(1)).delete(sampleNotes);
    }

    @Test
    public void testDeleteNotesWithInvalidId() {
        // Créez un identifiant de notes invalide
        String invalidNotesId = "999";

        // Faites en sorte que notesRepository.findById renvoie un Optional vide (identifiant invalide)
        when(notesRepository.findById(invalidNotesId)).thenReturn(Optional.empty());

        // Appelez la méthode de service pour supprimer les notes avec un identifiant invalide
        // La méthode devrait lancer une exception IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            notesService.deleteNotes(invalidNotesId);
        });

        // Vérifiez que l'exception a été lancée comme prévu
    }

    @Test
    public void testGetNoteByIdWithValidId() {
        // Créez un identifiant de notes valide
        String validNotesId = "123";

        // Créez un objet Notes simulé correspondant à l'identifiant valide
        Notes sampleNotes = new Notes();
        when(notesRepository.findById(validNotesId)).thenReturn(Optional.of(sampleNotes));

        // Appelez la méthode de service pour obtenir les notes par ID
        Optional<Notes> result = notesService.getNoteById(validNotesId);

        // Vérifiez que la méthode renvoie l'objet Optional contenant les notes simulées
        assertTrue(result.isPresent(), "getNoteById should return non-empty Optional");
        assertEquals(sampleNotes, result.get(), "getNoteById should return the expected Notes object");

        // Assurez-vous que notesRepository.findById a été appelé avec l'identifiant valide
        verify(notesRepository, times(1)).findById(validNotesId);
    }

    @Test
    public void testGetNoteByIdWithInvalidId() {
        // Créez un identifiant de notes invalide
        String invalidNotesId = "999";

        // Faites en sorte que notesRepository.findById renvoie un Optional vide (identifiant invalide)
        when(notesRepository.findById(invalidNotesId)).thenReturn(Optional.empty());

        // Appelez la méthode de service pour obtenir les notes avec un identifiant invalide
        Optional<Notes> result = notesService.getNoteById(invalidNotesId);

        // Vérifiez que la méthode renvoie un Optional vide
        assertFalse(result.isPresent(), "getNoteById should return an empty Optional");

        // Assurez-vous que notesRepository.findById a été appelé avec l'identifiant invalide
        verify(notesRepository, times(1)).findById(invalidNotesId);
    }

    @Test
    public void testGetNoteByPatId() {
        // Créez un identifiant de patient valide
        int validPatientId = 456;

        // Créez une liste de notes simulée correspondant à l'identifiant du patient valide
        List<Notes> sampleNotesList = new ArrayList<>();
        sampleNotesList.add(new Notes());
        sampleNotesList.add(new Notes());
        when(notesRepository.findByPatId(validPatientId)).thenReturn(sampleNotesList);

        // Appelez la méthode de service pour obtenir les notes par identifiant de patient
        List<Notes> result = notesService.getNoteByPatId(validPatientId);

        // Vérifiez que la méthode renvoie la liste de notes simulées
        assertEquals(sampleNotesList, result, "getNoteByPatId should return the expected list of Notes");

        // Assurez-vous que notesRepository.findByPatId a été appelé avec l'identifiant de patient valide
        verify(notesRepository, times(1)).findByPatId(validPatientId);
    }
}
