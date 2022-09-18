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
import com.p1.ek.model.objfiles.BookGenreLink;
import com.p1.ek.model.repos.AuthorRepo;
import com.p1.ek.model.repos.GenreRepo;
import com.p1.ek.model.objfiles.Author;
import com.p1.ek.model.objfiles.Genre;

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
                    new ArrayList<>(),
                    new ArrayList<>()
                    // ar.getAuthorsByBookId(rs.getInt("bookId")),
                    // gr.getGenresByBook(rs.getInt("bookId"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Get a book by its id from database.
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
                gotBook.setPublishDate(rs.getString("publishDate"));
                gotBook.setAuthors(ar.getAuthorsByBookId(rs.getInt("bookId")));
                gotBook.setGenres(gr.getGenresByBookId(rs.getInt("bookId")));
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
        String query = "SELECT * FROM BOOK b where b.title like ?;";
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

    public Book getBookByTitle(String title) {
        // List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM BOOK b where b.title = ?;";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setString(1, title);
            ResultSet rs = sqlStatement.executeQuery();

            if (rs.next()) {
                Book gotBook = new Book();
                gotBook.setBookId(rs.getInt("bookId"));
                gotBook.setTitle(rs.getString("title"));
                gotBook.setPrice(rs.getDouble("price")); 
                gotBook.setQuantity(rs.getInt("quantity")); 
                gotBook.setImgUrl(rs.getString("imgUrl"));
                gotBook.setIsbn(rs.getString("isbn"));
                gotBook.setPublishDate(rs.getString("publishDate"));
                gotBook.setAuthors(ar.getAuthorsByBookId(rs.getInt("bookId")));
                gotBook.setGenres(gr.getGenresByBookId(rs.getInt("bookId")));
                return gotBook;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

// THE ADDING METHODS

    // Add a new book to the database
    // (Thing to consider) allow the quantity of a book to increment when another book with the same information is added.
    // You must insert into both author and genre first before inserting into book!
    // Getting the relevant parts of the input model into their respective classes and tables.
    public void addBook(Book newBook) {
        List<Book> possBooks = this.getBooksByTitle(newBook.getTitle()); // Check if the book already exists; if it does, update book's quantity; if not, add it
        if (possBooks.size() != 0) {
            this.updateBookQuantity(newBook);
            // If you're just updating the quantity of a book, its authors and genres and their respective links
            // should already exist in the database, so no need to check them or update them.
        } else {

            String query = "insert into book(title, price, quantity, imgUrl, isbn, publishDate) " +
                            "values (?, ?, ?, ?, ?, ?)";
            try {
                // ar.addAuthors(newBook.getAuthors());
                // gr.addGenres(newBook.getGenres());

                PreparedStatement sqlStatement = db.prepareStatement(query);
                sqlStatement.setString(1, newBook.getTitle());
                sqlStatement.setDouble(2, newBook.getPrice());
                sqlStatement.setInt(3, newBook.getQuantity());
                sqlStatement.setString(4, newBook.getImgUrl());
                sqlStatement.setString(5, newBook.getIsbn());
                sqlStatement.setString(6, newBook.getPublishDate());
                sqlStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Updating the respective link tables in concert with adding new books
    // Each entry is expected to be unique, given the pairs
    // The book already should be checked if it is in the database
    // And the authors and genres should also already be checked for duplicates,
    // since the author and genre ids should already exist in their tables.
    // public void addBookLink(Book newBook) { // Insert Parameters do not have a reliable bookId, so don't use it.
    //     for (Author a : newBook.getAuthors()) {
    //         if (a != null) {
    //             String query = "insert into book_author_link(bookId, authorId) values(?, ?)";
    //             try {
    //                 PreparedStatement sqlStatement = db.prepareStatement(query);
    //                 sqlStatement.setInt(1, this.getBookByTitle(newBook.getTitle()).getBookId());
    //                 sqlStatement.setInt(2, ar.getAuthorByTitle(a.getFirstName(), a.getLastName()).getAuthorId());
    //                 sqlStatement.executeUpdate();
    //             } catch (SQLException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }

    //     // Now for the genres:
    //     for (Genre g : newBook.getGenres()) {
    //         if (g != null) {
    //             String query = "insert into book_genre_link(bookId, genreId) values(?, ?)";
    //             try {
    //                 PreparedStatement sqlStatement = db.prepareStatement(query);
    //                 sqlStatement.setInt(1, this.getBookByTitle(newBook.getTitle()).getBookId());
    //                 sqlStatement.setInt(2, g.getGenreId());
    //                 sqlStatement.executeUpdate();
    //             } catch (SQLException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }
    // }

// UPDATE METHODS:

    // Find the respective book in database and update its quantity field
    // Should only be called when the book is established to already exist.
    private void updateBookQuantity(Book newBook) {
        String query = "update book set quantity = quantity + 1 where bookId = ?";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setInt(1, newBook.getBookId());
            sqlStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Use a previously inserted book to update its record based on user input.
    // Also call authorrepo's and genrerepo's respective update methods.
    // Should only ever be called when updating; book was verified elsewhere.
    public void updateBook(Book modBook) {
        String query = "update book set title = ?, price = ?, quantity = ?, imgUrl = ?, isbn = ?, publishDate = ? " +
                        "where bookId = ?";
        
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setString(1, modBook.getTitle());
            sqlStatement.setDouble(2, modBook.getPrice());
            sqlStatement.setInt(3, modBook.getQuantity());
            sqlStatement.setString(4, modBook.getImgUrl());
            sqlStatement.setString(5, modBook.getIsbn());
            sqlStatement.setString(5, modBook.getPublishDate());
            sqlStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

// DELETE METHODS

    public void deleteBook(Book delBook) {
        String query = "delete book " +
                        "where bookId = ?";
        
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setInt(1, delBook.getBookId());
            sqlStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    
}
