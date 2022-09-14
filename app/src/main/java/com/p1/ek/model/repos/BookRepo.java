package com.p1.ek.model.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.p1.ek.model.dbconn.DB;
import com.p1.ek.model.objfiles.Book;
import com.p1.ek.model.repos.AuthorRepo;
import com.p1.ek.model.repos.GenreRepo;

// Only focus on getting the direct data from the db into the model classes.
// Coordinating that information together should be for the service classes.
public class BookRepo {

    private Connection db;
    private AuthorRepo ar;
    private GenreRepo gr;

    public BookRepo() {
        db = DB.connectToDb();
        ar = new AuthorRepo();
        gr = new GenreRepo();
    }

    // THE GETTING METHODS:
    
    // Get all the books in the database.
    // This also includes any related info
    // like genres and author
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOK b;";
        try {
            Statement sqlStatement = db.createStatement();
            ResultSet rs = sqlStatement.executeQuery(query);

            while (rs.next()) {
                // For a single book, get list of authors and genres
                books.add(new Book(
                    rs.getInt("bookId"),
                    rs.getString("title"),
                    rs.getDouble("price"), 
                    rs.getInt("quantity"), 
                    rs.getString("imgUrl"),
                    rs.getString("isbn"),
                    rs.getString("publishDate"),
                    ar.getAuthorsByBookId(rs.getInt("bookId")),
                    gr.getGenresByBookId(rs.getInt("bookId"))
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
        
        String query = "SELECT * FROM BOOK b WHERE b.bookId = ?;";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setInt(1, id);
            ResultSet rs = sqlStatement.executeQuery();

            if (rs.next()) {
                Book gotBook = new Book();
                gotBook.setBookId(rs.getInt("bookId"));
                gotBook.setTitle(rs.getString("title"));
                gotBook.setPrice(rs.getDouble("price")); 
                gotBook.setQuantity(rs.getInt("quantity")); 
                gotBook.setImgUrl(rs.getString("imgUrl"));
                gotBook.setIsbn(rs.getString("isbn"));
                    // rs.getInt("authorId"),
                gotBook.setPublishDate(rs.getString("publishDate"));
                return gotBook;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get books by a title.
    // There could be multiple versions of a book; therefore books with the same title.
    public List<Book> getBooksByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOK b where b.title = ?;";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setString(1, title);
            ResultSet rs = sqlStatement.executeQuery();

            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("bookId"),
                    rs.getString("title"),
                    rs.getDouble("price"), 
                    rs.getInt("quantity"), 
                    rs.getString("imgUrl"),
                    rs.getString("isbn"),
                    rs.getString("publishDate"),
                    ar.getAuthorsByBookId(rs.getInt("bookId")),
                    gr.getGenresByBookId(rs.getInt("bookId"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // THE ADDING METHODS

    // Add a new book to the database
    // (Thing to consider) allow the quantity of a book to increment when another book with the same information is added.
    public void addBook(Book newBook) {
        String query = "insert into book(title, price, quantity, imgUrl, isbn, publishDate) " +
                        "values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setString(1, newBook.getTitle());
            sqlStatement.setDouble(2, newBook.getPrice());
            sqlStatement.setInt(3, newBook.getQuantity());
            sqlStatement.setString(4, newBook.getImgUrl());
            sqlStatement.setString(5, newBook.getIsbn());
            sqlStatement.setString(5, newBook.getPublishDate());
            sqlStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
