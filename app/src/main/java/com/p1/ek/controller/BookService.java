package com.p1.ek.controller;

import com.p1.ek.model.repos.BookRepo;
import com.p1.ek.model.objfiles.Book;
import java.util.List;

// Use a single, composite record Book to get information for all the relevant services.
public class BookService {
    private BookRepo br;

    public BookService() {
        br = new BookRepo();
    }

    public void createRecord(Book newBook, AuthorService as, GenreService gs, BookAuthorLinkService bals, BookGenreLinkService bgls) {
        br.addBook(newBook);
        as.createRecord(newBook);
        gs.createRecord(newBook);
        bals.createRecord(newBook);
        bgls.createRecord(newBook);
    }
    public void updateRecord(Book modBook) {
        br.updateBook(modBook);
    }
    public List<Book> readRecords(AuthorService as, GenreService gs) {
        List<Book> allBooks = br.getAllBooks();
        for (Book b : allBooks) {
            b.setAuthors(as.readRecordsForBook(b));
            b.setGenres(gs.readRecordsForBook(b));
        }
        return allBooks;
    }
    public Book readRecord(int bookId) {
        return br.getBookById(bookId);
    }
    public void deleteRecord(Book delBook) {
        br.deleteBook(delBook);
    }
}
