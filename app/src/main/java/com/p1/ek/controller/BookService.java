package com.p1.ek.controller;

import com.p1.ek.model.repos.BookRepo;
import com.p1.ek.model.objfiles.Book;
import java.util.List;

public class BookService {
    private BookRepo br;

    public BookService() {
        br = new BookRepo();
    }
    public void createRecord() {

    }
    public void updateRecord() {

    }
    public List<Book> readRecords() {
        return br.getAllBooks();
    }
    public Book readRecord(int bookId) {
        return null;
    }
    public void deleteRecord() {
        
    }
}
