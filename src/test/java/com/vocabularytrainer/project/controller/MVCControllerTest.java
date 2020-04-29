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

@SpringBootTest
public class MVCControllerTest {


    @Autowired
    private MVCController controller;

    @MockBean
    private VocabularyRepository vocabularyRepository;

    @Mock
    private Model model;

    // security mocks start
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @Mock
    private UserDetails userDetails;
    // security mocks end


    // global test variables
    private static final String username = "John Test";


    @BeforeEach
    public void beforeEach() {
        // init mocks
        MockitoAnnotations.initMocks(this);


        // spring security setup
        when(this.securityContext.getAuthentication()).thenReturn(this.authentication);
        when(this.authentication.getPrincipal()).thenReturn(this.userDetails);
        when(this.userDetails.getUsername()).thenReturn(username);

        SecurityContextHolder.setContext(this.securityContext);

        // database setup
        this.vocabularyRepository.deleteAll();
    }

    /**
     * Verify security context calls
     */
    @AfterEach
    public void afterEach() {
        verify(this.securityContext, times(1)).getAuthentication();
        verify(this.authentication, times(1)).getPrincipal();
    }

    @Test
    public void testUserIndexEmpty() {
        this.controller.userIndex(this.model);

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



}
