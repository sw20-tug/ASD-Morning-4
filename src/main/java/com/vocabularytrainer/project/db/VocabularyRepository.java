package com.vocabularytrainer.project.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *  JpaRepository = Sub Class of CrudRepository
 *  CrudRepository - "connection layer" - from spring data representation
 *  This allows us to use CRUD Operations (Create, Read, Update and Delete)
 *
 *  Alternatively, we could've setup our own "connection layer" by applying DAO principles (data access objects)
 *
 *  Persistence = JPA -> ORM (object relative mapping) standard in a relational database
 *
 *  The repository's single responsibility is to query the data base.
 *
 *  */

// with this, we can make the connection to the database - "connection layer"
// JPA = Java Persistence API for managing relational data (=Database)
// We use JPQL (="SQL" queries in Java) to make queries

// JPA repositories are not picked up by component scans since they are just
// interfaces whos concrete classes are created dynamically as beans by Spring Data provided -> Bean
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

    @Query(value="SELECT j FROM VocabularyEntries j WHERE j.user = :paruser AND j.id IN :ids")
    List<VocabularyEntries> getSelectedIdsFromUserX(
        @Param("paruser") String paruser,
        @Param("ids") List<Integer> ids
    );
}
