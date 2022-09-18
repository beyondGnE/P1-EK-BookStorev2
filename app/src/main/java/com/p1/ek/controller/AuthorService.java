package com.p1.ek.controller;

import com.p1.ek.model.objfiles.Author;
import com.p1.ek.model.objfiles.Book;
import com.p1.ek.model.repos.AuthorRepo;

import java.util.List;

public class AuthorService {
    private AuthorRepo ar;
    
    public AuthorService() {
        ar = new AuthorRepo();
    }

    public void createRecord(Book newBook) {
        ar.addAuthors(newBook);
    }

    public List<Author> readRecords() {
        return ar.getAuthors();
    }

    public List<Author> readRecordsForBook(Book newBook) {
        return ar.getAuthorsByBook(newBook);
    }

    public void updateRecord() {

    }
}
