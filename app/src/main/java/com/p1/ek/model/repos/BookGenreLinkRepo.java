package com.p1.ek.model.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.p1.ek.model.dbconn.DB;
import com.p1.ek.model.objfiles.Book;
import com.p1.ek.model.objfiles.Genre;

public class BookGenreLinkRepo {
    
    private Connection db;

    public BookGenreLinkRepo() {
        db = DB.connectToDb();
    }
    public void addBookGenreLink(Book newBook) {
        String query = "insert into book_genre_link (bookId, genreId) values (?, ?)";
        try {
            // Must assume each book might have multiple authors
            // A book might have been entered without any authors, but that's fine.
            for (Genre g : newBook.getGenres()) {
                PreparedStatement sqlStatement = db.prepareStatement(query);
                sqlStatement.setInt(1, newBook.getBookId());
                sqlStatement.setInt(2, g.getGenreId());
                sqlStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBookLink(Book modBook) {
        String query = "delete from book_genre_link where bookId = ? and genreId = ?";
        try {
            for (Genre g : modBook.getGenres()) {
                PreparedStatement sqlStatement = db.prepareStatement(query);
                sqlStatement.setInt(1, modBook.getBookId());
                sqlStatement.setInt(2, g.getGenreId());
                sqlStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // No updates for book links are necessary -- it is far more prudent to delete and add link records instead.
    // public void updateBookLink(Book modBook) {
    //     String query = "update (select * from genre g inner join book_genre_link bgl " +
    //                     "on g.genreId = bgl.genreId " +
    //                     "inner join book b on bgl.bookId = b.bookId " +
    //                     "where b.bookId = ?) " +
    //                     "set bal.authorId = ? where b.bookId = ?";
    //     try {
    //         for (Genre g : modBook.getGenres()) {
    //             PreparedStatement sqlStatement = db.prepareStatement(query);
    //             sqlStatement.setInt(1, modBook.getBookId());
    //             sqlStatement.setInt(3, g.getGenreId());
    //             sqlStatement.setInt(4, modBook.getBookId());
    //             sqlStatement.executeUpdate();
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
}
