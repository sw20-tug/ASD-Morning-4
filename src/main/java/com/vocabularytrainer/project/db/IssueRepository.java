package com.vocabularytrainer.project.db;

import java.util.List;

import com.vocabularytrainer.project.entity.IssueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueRepository extends JpaRepository<IssueReport, Integer> {

    // Method to show everything but private
    @Query(value = "SELECT i FROM IssueReport i WHERE markedAsPrivate = false")
    List<IssueReport> findAllButPrivate();

    // Method to showw everything with email = input string
    @Query("SELECT u FROM IssueReport u WHERE u.email = :name")
    List<IssueReport> findAllByEmail(
      @Param("name") String name
    );
}
