package com.vocabularytrainer.project.db;

// Entity-Model for Database Table

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Entity // tells Hibernate ("database manager for java") this is a database table
@Table(name = "tabAllUserVocabularies")
public class VocabularyAddEntries {

    /* Model of Table */
    @Id // these are some options for id to be auto-increment
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String user;
    private String german_word;
    private String engl_trans;
    private int rating; // for Issue LANG_006
    private String tag; // for Issue LANG_005 ("custom tags for filtering")

    // Standard Constructor e.g. called in getUserAddVocabulary()
    public VocabularyAddEntries() {

    }

    // getters/setters auto generation
    // right-click->generate
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGerman_word() {
        return german_word;
    }

    public void setGerman_word(String german_word) {
        this.german_word = german_word;
    }

    public String getEngl_trans() {
        return engl_trans;
    }

    public void setEngl_trans(String engl_trans) {
        this.engl_trans = engl_trans;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
