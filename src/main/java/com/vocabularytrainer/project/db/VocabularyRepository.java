package com.vocabularytrainer.project.db;

import com.vocabularytrainer.project.db.VocabularyAddEntries; // database entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// with this, we can make the connection to the database - "connection layer"
// JPA = Java Persistence API for managing relational data (=Database)
// We use JPQL (="SQL" queries in Java) to make queries
public interface VocabularyRepository extends JpaRepository<VocabularyAddEntries, Integer> {

    /* Queries */

    @Query(value="SELECT u FROM VocabularyAddEntries u")
    List<VocabularyAddEntries> showAllVocabulary();

    @Query(value="SELECT x FROM VocabularyAddEntries x WHERE x.user = :paruser")
    // using <List> Java Template we save everything in there ad use as Data Type the table
    List<VocabularyAddEntries> showAllVocabularyFromUserX(
        @Param("paruser") String paruser // Parameter Handling for JPQL-Style queries
    );


}
