package com.vocabularytrainer.project.CSVParser;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.vocabularytrainer.project.db.VocabularyEntries;
import com.vocabularytrainer.project.db.VocabularyRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVWriter {

    public static File writeAllEntriesToCSV(VocabularyRepository vocabularyRepository, UserDetails userDetails, String filename, List<Integer> ids)
    {
        // get vocabularies entries and parse to Array
        List<VocabularyEntries> vocabularyEntries = vocabularyRepository.showAllVocabularyFromUserX(userDetails.getUsername());

        if(!ids.isEmpty())
        {
            vocabularyEntries = vocabularyRepository.getSelectedIdsFromUserX(userDetails.getUsername(), ids);
        }

        try {

            // create mapper and schema
            CsvMapper mapper = new CsvMapper();
            mapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
            CsvSchema schema = mapper.schemaFor(VocabularyEntries.class);
            schema = schema.withColumnSeparator(';');
            schema = schema.withUseHeader(true);

            // output writer
            File file = new File(filename);
            FileOutputStream tempFileOutputStream = new FileOutputStream(file);
            OutputStreamWriter writerOutputStream = new OutputStreamWriter(tempFileOutputStream, StandardCharsets.UTF_8);
            mapper.writer(schema).writeValue(writerOutputStream, vocabularyEntries);

            System.out.println("Successfully created CSV!\n");

            return file;

        } catch(Exception ex) {

            System.out.println(ex.getMessage());

            return null;
        }
    }
}
