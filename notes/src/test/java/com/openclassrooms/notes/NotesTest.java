package com.openclassrooms.notes;

import com.openclassrooms.notes.model.Notes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotesTest {

    @Test
    public void testGettersAndSetters() {
        // Créez une instance de Notes
        Notes notes = new Notes();

        // Définissez des valeurs pour les propriétés
        notes.setId("1");
        notes.setPatId(123);
        notes.setRisk("High");
        notes.setNoteContent("This is a test note.");

        // Vérifiez que les valeurs sont correctement récupérées à l'aide des getters
        assertEquals("1", notes.getId());
        assertEquals(123, notes.getPatId());
        assertEquals("High", notes.getRisk());
        assertEquals("This is a test note.", notes.getNoteContent());

        // Modifiez les valeurs en utilisant les setters
        notes.setId("2");
        notes.setPatId(456);
        notes.setRisk("Medium");
        notes.setNoteContent("Updated note content.");

        // Vérifiez que les nouvelles valeurs sont correctes
        assertEquals("2", notes.getId());
        assertEquals(456, notes.getPatId());
        assertEquals("Medium", notes.getRisk());
        assertEquals("Updated note content.", notes.getNoteContent());
    }
}

