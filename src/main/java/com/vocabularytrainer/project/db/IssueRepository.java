package com.vocabularytrainer.project.db;

import java.util.List;

import com.vocabularytrainer.project.entity.IssueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IssueRepository extends JpaRepository<IssueReport, Integer> {

    // Method to show everything but private
    @Query(value = "SELECT i FROM IssueReport i WHERE markedAsPrivate = false")
    List<IssueReport> findAllButPrivate();

    // this also works as a method
    List<IssueReport> findAllByEmail(String email);
}
