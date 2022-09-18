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
    public void updateRecord(Book modBook, AuthorService as, GenreService gs, BookAuthorLinkService bals, BookGenreLinkService bgls) {
        br.updateBook(modBook);
        as.updateRecord(modBook);
        gs.updateRecord(modBook);
        bals.updateRecord(modBook);
        bgls.updateRecord(modBook);
        // bals.updateRecord(modBook);
        // bgls.updateRecord(modBook);
    }
    public List<Book> readRecords(AuthorService as, GenreService gs) {
        List<Book> allBooks = br.getAllBooks();
        for (Book b : allBooks) {
            b.setAuthors(as.readRecordsForBook(b));
            b.setGenres(gs.readRecordsForBook(b));
        }
        return allBooks;
    }
    public Book readRecord(int bookId, AuthorService as, GenreService gs) {
        Book gotBook = br.getBookById(bookId);
        gotBook.setAuthors(as.readRecordsForBook(gotBook));
        gotBook.setGenres(gs.readRecordsForBook(gotBook));
        return gotBook;
    }
    public void deleteRecord(Book delBook, BookAuthorLinkService bals, BookGenreLinkService bgls) {
        br.deleteBook(delBook);
        bals.deleteRecord(delBook);
        bgls.deleteRecord(delBook);
    }
}
