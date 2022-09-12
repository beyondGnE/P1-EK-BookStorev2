package com.p1.ek.controller;

import com.p1.ek.model.objfiles.Author;
import com.p1.ek.model.repos.AuthorRepo;

import java.util.List;

public class AuthorService {
    private AuthorRepo ar;
    
    public AuthorService() {
        ar = new AuthorRepo();
    }

    public void createRecord(Author newAuthor) {
        ar.addAuthor(newAuthor);
    }

    public List<Author> readRecords() {
        return ar.getAuthors();
    }

    public void updateRecord() {

    }
}
