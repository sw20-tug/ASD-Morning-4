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

/** test: testGetUserAddVocabulary */

@SpringBootTest
public class MVCControllerNoSecurityTest {


    @Autowired
    private MVCController controller;

    @MockBean
    private VocabularyRepository vocabularyRepository;

    @Mock
    private Model model;



    // Ein globaler Test-String
    private static final String username = "John Test";

    // @BeforeEach signalisiert, dass diese Methode vor jeder Test-Annotation
    // durchgeführt werden soll. "@Test" sind Testmethoden.
    @BeforeEach
    public void beforeEach() {
        // Initialisiert die Feld-Annotationen mit Mockito-Annotationen.
        MockitoAnnotations.initMocks(this);

        // Die Datenbank wird eingestellt. In diesem Fall werden die Einträge gelöscht.
        this.vocabularyRepository.deleteAll();
    }


    @Test
    public void testGetUserAddVocabulary() {
        // Die Methode getUserAddVocuabulary wird aufgerufen.
        // Das Modell dieser Klasse wird als Argument übergeben - jedoch hat
        // das Model keine Attribute.
        this.controller.getUserAddVocabulary(this.model);

        // Mit "ArgumentCaptor" können Argumente abgefangen werden, sodass man mit
        // diesen weitere Tests durchführen kann. In diesem Fall werden die Argumente
        // der Klasse "VocabularyEntries" abgefangen. Mit getValue() bekommt man den Wert
        // eines Arguments.
        ArgumentCaptor<VocabularyEntries> argument = ArgumentCaptor.forClass(VocabularyEntries.class);
        verify(this.model, times(1)).addAttribute(any(), argument.capture());

        // Mit "assertEquals" überprüft man, ob ein Output "gleich" ist, wie der erwartete Output.
        // In diesem Fall sollte der Output für die ID "0" und für den User "null" sein.
        assertEquals(0, argument.getValue().getId());
        assertEquals(null, argument.getValue().getUser());

    }

}
