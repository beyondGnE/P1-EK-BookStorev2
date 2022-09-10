package com.p1.ek.model.repos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.p1.ek.model.dbconn.DB;
import com.p1.ek.model.objfiles.Book;


public class BookRepo {

    private Connection db;

    public BookRepo() {
        db = DB.connectToDb();
    }

    // THE GETTING METHODS:
    
    // Get all the books in the database.
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOK b INNER JOIN AUTHOR a "+
                        "ON b.authorId = a.authorId";
        try {
            Statement sqlStatement = db.createStatement();
            ResultSet rs = sqlStatement.executeQuery(query);

            while (rs.next()) {
                books.add(new Book(
                    rs.getString("title"),
                    rs.getDouble("price"), 
                    rs.getInt("quantity"), 
                    rs.getString("imgUrl"),
                    rs.getString("isbn"),
                    rs.getInt("authorId"),
                    rs.getInt("bookId"),
                    rs.getString("publishDate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Get a book by its id.
    // Should always return one book, since book ids are unique.
    public Book getBookById(int id) {
        return null;
    }

    // Get books by a title.
    // There could be multiple versions of a book; therefore books with the same title.
    public List<Book> getBooksByTitle(String title) {
        return null;
    }

    // THE ADDING METHODS

    // Add a new book to the database
    public void addBook(Book newBook) {

    }
}
