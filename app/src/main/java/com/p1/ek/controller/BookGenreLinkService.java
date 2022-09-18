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
}
