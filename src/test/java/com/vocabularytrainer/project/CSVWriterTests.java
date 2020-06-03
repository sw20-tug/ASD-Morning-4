package com.vocabularytrainer.project;


import com.vocabularytrainer.project.CSVParser.CSVWriter;
import com.vocabularytrainer.project.db.VocabularyRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CSVWriterTests {

    @Mock
    private VocabularyRepository vocabularyRepository; // MVCController is dependent on VocabularyRepository -> MockBean VocabularyRepository

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private CSVWriter csvWriter;

    private String savedFilePath;

    @Before
    public void before() {

        MockitoAnnotations.initMocks(this);

        when(this.userDetails.getUsername()).thenReturn("TestUser");

        String filepath = "src/main/resources/templates/user/csv-output/";
        String filename = filepath + "export_vocabularies_" + userDetails.getUsername() + ".csv";

        File file = new File(filename);
        savedFilePath = file.getAbsolutePath();
    }

    @Test
    public void testPath() {

        //System.out.println(savedFilePath);

        assertTrue(savedFilePath.endsWith("/Users/Simon/git-repos/ASD-Morning-4/src/main/resources/templates/user/csv-output/export_vocabularies_TestUser.csv"));

    }

    @Test
    public void testFileCreation()
    {

        List<Integer> ids = Arrays.asList();

        File file = csvWriter.writeAllEntriesToCSV(vocabularyRepository, userDetails, savedFilePath, ids);

        assertNotNull(file);

        //System.out.print(file.toString());

    }
}