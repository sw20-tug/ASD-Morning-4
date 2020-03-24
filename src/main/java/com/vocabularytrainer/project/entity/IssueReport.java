package com.vocabularytrainer.project.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;

@Entity // tell hibernate that this is a database table
@Table(name = "issues") // if this is not present, the class name = table name
public class IssueReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Members of the table

    // The values of the fields email, url, description, markedAsPrivate and updates will be coming
    // from the form the user submitted. The others will be generated on creation or when
    // the report is updated.
    private int id;
    private String email;
    private String url;
    private String description;
    private boolean markedAsPrivate;
    private boolean updates;
    private boolean done;
    private Date created;
    private Date updated;

    // Constructor
    public IssueReport() {

    }

    // Getters
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMarkedAsPrivate() {
        return markedAsPrivate;
    }

    public boolean isUpdates() {
        return updates;
    }

    public boolean isDone() {
        return done;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {

        //this.email = email; // uses that from thymeleaf variable

        //set the current user to it -> make "automatically"

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        this.email = userDetails.getUsername();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMarkedAsPrivate(boolean markedAsPrivate) {
        this.markedAsPrivate = markedAsPrivate;
    }

    public void setUpdates(boolean updates) {
        this.updates = updates;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}