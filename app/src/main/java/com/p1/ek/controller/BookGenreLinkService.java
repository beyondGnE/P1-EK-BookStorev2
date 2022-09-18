package com.p1.ek.controller;

import com.p1.ek.model.objfiles.Book;
import com.p1.ek.model.repos.BookGenreLinkRepo;

public class BookGenreLinkService {
    private BookGenreLinkRepo bglr;

    public BookGenreLinkService() {
        bglr = new BookGenreLinkRepo();
    }

    public void createRecord(Book newBook) {
        bglr.addBookGenreLink(newBook);
    }

    public void updateRecord(Book modBook) {
        bglr.deleteBookLink(modBook);
        bglr.addBookGenreLink(modBook);
    }

    public void deleteRecord(Book delBook) {
        bglr.deleteBookLink(delBook);
    }
}
