package com.p1.ek.controller;

import com.p1.ek.model.objfiles.Book;
import com.p1.ek.model.repos.BookAuthorLinkRepo;

public class BookAuthorLinkService {
    private BookAuthorLinkRepo balr;

    public BookAuthorLinkService() {
        balr = new BookAuthorLinkRepo();
    }

    public void createRecord(Book newBook) {
        balr.addBookAuthorLink(newBook);
    }

    public void updateRecord(Book modBook) {
        // balr.updateLink(modBook);
        balr.deleteBookLink(modBook);
        balr.addBookAuthorLink(modBook);
    }

    public void deleteRecord(Book delBook) {
        balr.deleteBookLink(delBook);
    }
}
