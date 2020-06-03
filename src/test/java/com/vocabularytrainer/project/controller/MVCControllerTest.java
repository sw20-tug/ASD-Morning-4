package com.vocabularytrainer.project.controller;

import com.vocabularytrainer.project.db.VocabularyEntries;
import com.vocabularytrainer.project.db.VocabularyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test 1: If the output of the database is nothing (when it was deleted previously)
 * Test 2: If the "overview" function works as intended and if function "showing the logged in user" works
 * Test 3: If submitted added new vocabulary was added to the database
 * Test 4: If edit Tag function is working
 * Test 5: testSubmitEditedVocabularyEntry
 * Test 6: testUserStudyInterfaceEnglish
 * Test 7: testUserStudyInterfaceGerman
 * Test 8: testDeleteVocabularyEntry
 * */

@SpringBootTest
public class MVCControllerTest {

    @Autowired
    private MVCController controller;

    @MockBean
    private VocabularyRepository vocabularyRepository; // MVCController is dependent on VocabularyRepository -> MockBean VocabularyRepository

    @Mock
    private Model model; // Mock model -> Thymeleaf dependency -> "model manipulation" in templates variables

    // security mocks start
    @Mock
    private SecurityContext securityContext; // also Mock -> Dependency from SpringSecurity
    @Mock
    private Authentication authentication;
    @Mock
    private UserDetails userDetails;
    // security mocks end


    // global test variables
    private static final String username = "John Test";


    @BeforeEach
    public void beforeEach() {
        // Initialisiere Mocks
        MockitoAnnotations.initMocks(this); // SpringBootTest --> this class
                                                     // Import Package

        // Hier legen wir ein Return-Verhalten für unterschiedliche Methoden fest.
        // z. B. Wenn "getAuthentiication()" aufgerufen wird, gibt die
        // Instanz "authentication" der Klasse Athentication zurück.
        when(this.securityContext.getAuthentication()).thenReturn(this.authentication);
        when(this.authentication.getPrincipal()).thenReturn(this.userDetails);
        when(this.userDetails.getUsername()).thenReturn(username);

        // Ordnet dem aktuellen Thread einen Sicherheitskontext zu. Anders formuliert, wir
        // spezifizieren die "Sicherheitsstrategie" für die virtuelle Maschine (JVM).
        SecurityContextHolder.setContext(this.securityContext);

        // Alle Datenbankeinträge werden gelöscht, um zu testen, wie das Programm darauf reagiert.
        this.vocabularyRepository.deleteAll();
    }

    // @AfterEach ist eine Annotation, die angibt, was nach jedem Test getestet werden soll.
    @AfterEach
    public void afterEach() {
        // "verify" wird genutzt, um zu testen, ob ein bestimmtes Verhalten eintrifft.
        // Wir können ebenfalls angeben, wie oft dafür die Mehtode aufgerufen werden soll.
        // Wenn der Aufruf funktioniert, wird er "verifiziert": Alles funktioniert.
        verify(this.securityContext, times(1)).getAuthentication();
        verify(this.authentication, times(1)).getPrincipal();
    }

    @Test
    public void testUserIndexEmpty() {
        this.controller.userIndex(this.model);

        // "any()" ist ein Platzhalter, falls der Input keine Rolle spielen sollte.
        verify(this.model, times(2)).addAttribute(any(), any());
        verify(this.model, times(1)).addAttribute("current_user", username);
        verify(this.model, times(1)).addAttribute("overview", new ArrayList());
    }


    @Test
    public void testUserIndex() {

        //setup db start
        VocabularyEntries ve = new VocabularyEntries();
        List<VocabularyEntries> veList = new ArrayList<>();
        veList.add(ve);

        when(this.vocabularyRepository.showAllVocabularyFromUserX(username)).thenReturn(veList);
        //setup db end

        // test controller
        this.controller.userIndex(this.model);

        verify(this.model, times(2)).addAttribute(any(), any());
        verify(this.model, times(1)).addAttribute("current_user", username);
        verify(this.model, times(1)).addAttribute("overview", veList);


        // verify db
        verify(this.vocabularyRepository, times(1)).showAllVocabularyFromUserX(username);

    }


    @Test
    public void testSubmitUserAddVocabulary() {

        //setup db start
        VocabularyEntries ve = new VocabularyEntries();
        when(this.vocabularyRepository.save(ve)).thenReturn(ve);
        //setup db end


        this.controller.submitUserAddVocabulary(ve, this.model);

        verify(this.model, times(1)).addAttribute("submitted", true);
        assertEquals(username, ve.getUser());
        verify(this.vocabularyRepository, times(1)).save(ve);
        verify(this.model, times(1)).addAttribute("addvoc", ve);
    }

    @Test
    public void testSubmitEditedTagEntry() {

        //setup db start
        VocabularyEntries ve = new VocabularyEntries();
        when(this.vocabularyRepository.save(ve)).thenReturn(ve);
        //setup db end


        this.controller.submitEditedTagEntry(0, ve, this.model);

        verify(this.model, times(1)).addAttribute("submitted", true);
        assertEquals(username, ve.getUser());
        verify(this.vocabularyRepository, times(1)).save(ve);
        verify(this.model, times(1)).addAttribute("edit_tag", ve);
    }

    @Test
    public void testSubmitEditedVocabularyEntry() {
        //setup db start
        VocabularyEntries ve = new VocabularyEntries();
        when(this.vocabularyRepository.save(ve)).thenReturn(ve);
        //setup db end

        this.controller.submitEditedVocabularyEntry(ve.getId(), ve, this.model);

        verify(this.model, times(1)).addAttribute("submitted", true);
        assertEquals(username, ve.getUser());
        verify(this.vocabularyRepository, times(1)).save(ve);
        verify(this.model, times(1)).addAttribute("editvoc", ve);
    }

    @Test
    public void testUserStudyInterfaceEnglish() {

        //setup db start
        VocabularyEntries ve = new VocabularyEntries();
        List<VocabularyEntries> veList = new ArrayList<>();
        veList.add(ve);
        when(this.vocabularyRepository.showAllVocabularyFromUserX(username)).thenReturn(veList);
        //setup db end
        // test controller
        this.controller.userStudyInterfaceEnglish(this.model);

        verify(this.model, times(2)).addAttribute(any(), any());
        verify(this.model, times(1)).addAttribute("current_user", username);
        verify(this.model, times(1)).addAttribute("overview", veList);

        // verify db
        verify(this.vocabularyRepository, times(1)).showAllVocabularyFromUserX(username);

    }

    @Test
    public void testUserStudyInterfaceGerman() {

        //setup db start
        VocabularyEntries ve = new VocabularyEntries();
        List<VocabularyEntries> veList = new ArrayList<>();
        veList.add(ve);
        when(this.vocabularyRepository.showAllVocabularyFromUserX(username)).thenReturn(veList);
        //setup db end
        // test controller
        this.controller.userStudyInterfaceGerman(this.model);

        verify(this.model, times(2)).addAttribute(any(), any());
        verify(this.model, times(1)).addAttribute("current_user", username);
        verify(this.model, times(1)).addAttribute("overview", veList);

        // verify db
        verify(this.vocabularyRepository, times(1)).showAllVocabularyFromUserX(username);

    }

    @Test
    public void testDeleteVocabularyEntry() {

        //setup db start
        VocabularyEntries ve = new VocabularyEntries();
        List<VocabularyEntries> veList = new ArrayList<>();
        veList.add(ve);
        when(this.vocabularyRepository.showAllVocabularyFromUserX(username)).thenReturn(veList);
        //setup db end

        this.controller.deleteVocabularyEntry(ve.getId(), this.model);
        ve = this.vocabularyRepository.getEntryBasedOnId(ve.getId());

        verify(this.vocabularyRepository, times(1)).delete(ve);
        verify(this.model, times(1)).addAttribute("overview", veList);
    }



    /*
    @Test
    public void testBoundarySubmitEditedTagEntry() {

        VocabularyEntries ve = new VocabularyEntries();
        ve.setId(0);
        ve.setTag("");
        when(this.vocabularyRepository.save(ve)).thenReturn(ve);

        this.controller.submitEditedTagEntry(0, ve, this.model);

        verify(this.model, times(1)).addAttribute("submitted", false);

        ve.setId(0);
        ve.setTag("a ridiculously long tag that should be not allowed.");
        when(this.vocabularyRepository.save(ve)).thenReturn(ve);

        this.controller.submitEditedTagEntry(0, ve, this.model);

        verify(this.model, times(1)).addAttribute("submitted", false);

        ve.setId(0);
        ve.setTag("animals");
        when(this.vocabularyRepository.save(ve)).thenReturn(ve);

        this.controller.submitEditedTagEntry(0, ve, this.model);
        verify(this.model, times(1)).addAttribute("submitted", true);
        assertEquals(username, ve.getUser());
    }*/

        /*
        -- model.addAttribute("submitted", true);

        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        vocabularyEntries.setUser(userDetails.getUsername());

        // save result in our Repository Interface
        VocabularyEntries result = this.vocabularyRepository.save(vocabularyEntries);

        // Thymeleaf-variable for "post-form" - add it
        model.addAttribute("edit_tag", result);

        return "user/edit_tag";
         */
}
