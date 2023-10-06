package com.clientui.mediscreen.api;

import com.clientui.mediscreen.controller.HomeController;
import com.clientui.mediscreen.controller.NotesController;
import com.clientui.mediscreen.domain.NotesBean;
import com.clientui.mediscreen.domain.PatientBeans;
import com.clientui.mediscreen.repository.MsNotesProxy;
import com.clientui.mediscreen.repository.MsPatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MediscreenControllerTest {

    @InjectMocks
    private NotesController notesController;

    @Mock
    private MsNotesProxy notesProxy;

    @Mock
    private MsPatientProxy patientProxy;


    private HomeController homeController;

    @BeforeEach
    public void setUp() {
        homeController = new HomeController();
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testHome() {
        Model mockModel = mock(Model.class);

        String viewName = homeController.home();

        assertEquals("home", viewName, "home should return 'home'");
    }

    @Test
    public void testView() {
        List<PatientBeans> patients = new ArrayList<>();
        List<NotesBean> notes = new ArrayList<>();

        when(patientProxy.listPatients()).thenReturn(patients);
        when(notesProxy.getAllNotes()).thenReturn(notes);

        Model mockModel = mock(Model.class);

        String viewName = notesController.view(mockModel).toString();

        assertEquals("notes/list", viewName, "view should return 'notes/list'");

        verify(mockModel).addAttribute("notes", notes);
        verify(mockModel).addAttribute("patient", patients);
    }

    @Test
    public void testAddNotes() {
        Model mockModel = mock(Model.class);

        when(notesController.addNotes(eq(1), any(Model.class))).thenReturn("notes/add");

        String viewName = notesController.addNotes(1, mockModel);

        assertEquals("notes/add", viewName, "addNotes should return 'notes/add'");

        verify(mockModel).addAttribute(eq("notes"), any(NotesBean.class));
        verify(mockModel).addAttribute(eq("patient"), any(PatientBeans.class));
    }






}
