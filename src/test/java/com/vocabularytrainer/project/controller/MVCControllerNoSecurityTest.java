package com.vocabularytrainer.project.controller;


import com.vocabularytrainer.project.db.VocabularyEntries;
import com.vocabularytrainer.project.db.VocabularyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MVCControllerNoSecurityTest {


    @Autowired
    private MVCController controller;

    @MockBean
    private VocabularyRepository vocabularyRepository;

    @Mock
    private Model model;


    // global test variables
    private static final String username = "John Test";


    @BeforeEach
    public void beforeEach() {
        // init mocks
        MockitoAnnotations.initMocks(this);

        // database setup
        this.vocabularyRepository.deleteAll();
    }


    @Test
    public void testGetUserAddVocabulary() {

        this.controller.getUserAddVocabulary(this.model);

        ArgumentCaptor<VocabularyEntries> argument = ArgumentCaptor.forClass(VocabularyEntries.class);
        verify(this.model, times(1)).addAttribute(any(), argument.capture());

        assertEquals(0, argument.getValue().getId());
        assertEquals(null, argument.getValue().getUser());

    }

}
