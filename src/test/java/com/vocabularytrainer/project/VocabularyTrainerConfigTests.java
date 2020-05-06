package com.vocabularytrainer.project;

import static org.junit.Assert.*;

import com.vocabularytrainer.project.controller.MVCController;
import com.vocabularytrainer.project.db.VocabularyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Tests if Spring is configured and all dependencies (db, etc.) are accessible.
 */

// Diese Annotation markiert, dass diese Klasse SpringBoot-spezifische Tests
// bereitstellt.
@SpringBootTest
public class VocabularyTrainerConfigTests {

    // Eine Autowired-Annotationen bewirkt eine automatische Verdrahtung - wie der Name bereits verrät.
    // D. h. Diese Methode wird ausgelöst, wenn eine Instanz von der Klasse erzeugt wird, in der sie
    // enthalten ist.
    @Autowired
    private MVCController controller;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Test
    // Indem wir bei dieser Methode "throws Exception" an den Methodenkopf anfügen,
    // wird eine auftretende Exception nicht in dieser Methode behandelt, sondern
    // an die aufrunfende Methode übergeben.
    public void testLoadContext() throws Exception {
        // "assertNotNull" überprüft, ob ein Objekt null "ist" oder nicht. Wenn es den Wert null
        // enthält, wirft (throws) es einen "AssertionError".
        assertNotNull(this.controller);
        assertNotNull(this.vocabularyRepository);
    }


    @Test
    public void testSecurityContext() throws Exception {
        assertNotNull(SecurityContextHolder.getContext());
    }
}
