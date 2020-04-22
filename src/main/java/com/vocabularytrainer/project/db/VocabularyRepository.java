package com.vocabularytrainer.project.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// with this, we can make the connection to the database - "connection layer"
// JPA = Java Persistence API for managing relational data (=Database)
// We use JPQL (="SQL" queries in Java) to make queries
public interface VocabularyRepository extends JpaRepository<VocabularyEntries, Integer> {

    /* Queries */

    @Query(value="SELECT u FROM VocabularyEntries u")
    List<VocabularyEntries> showAllVocabulary();

    @Query(value="SELECT x FROM VocabularyEntries x WHERE x.user = :paruser")
    // using <List> Java Template we save everything in there ad use as Data Type the table
    List<VocabularyEntries> showAllVocabularyFromUserX(
        @Param("paruser") String paruser // Parameter Handling for JPQL-Style queries
    );

    @Query(value="SELECT i FROM VocabularyEntries i WHERE i.id = :parid")
    VocabularyEntries getEntryBasedOnId(
        @Param("parid") int parid
    );
}
