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
@SpringBootTest
public class VocabularyTrainerConfigTests {

    @Autowired
    private MVCController controller;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Test
    public void testLoadContext() throws Exception {
        assertNotNull(this.controller);
        assertNotNull(this.vocabularyRepository);
    }


    @Test
    public void testSecurityContext() throws Exception {
        assertNotNull(SecurityContextHolder.getContext());
    }
}
