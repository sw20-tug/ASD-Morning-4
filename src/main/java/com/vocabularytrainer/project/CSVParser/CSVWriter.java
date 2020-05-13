package com.vocabularytrainer.project.CSVParser;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.vocabularytrainer.project.db.VocabularyEntries;
import com.vocabularytrainer.project.db.VocabularyRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class CSVWriter {

    public static void writeAllEntriesToCSV(VocabularyRepository vocabularyRepository, UserDetails userDetails)
    {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(VocabularyEntries.class);
        schema.withColumnSeparator(' ');

        // get vocabularies entries and parse to string
        List<VocabularyEntries> vocabularyEntries= vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername());

        // output writer
        ObjectWriter objectWriter = mapper.writer(schema);

        String filename = "export_vocabularies_" + userDetails.getUsername() + ".csv";
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File fileWithRelativePath = new File(tempDirectory, filename);

        try {

            fileWithRelativePath.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(fileWithRelativePath);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);
            OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
            objectWriter.writeValue(writerOutputStream, vocabularyEntries);

        } catch(Exception ex) {

            System.out.println(ex.getMessage());

        }
    }
}
