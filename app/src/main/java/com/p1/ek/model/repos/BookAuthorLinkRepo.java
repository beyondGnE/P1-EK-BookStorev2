package com.p1.ek.model.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.p1.ek.model.dbconn.DB;
import com.p1.ek.model.objfiles.Book;

import com.p1.ek.model.objfiles.Author;

public class BookAuthorLinkRepo {
    private Connection db;

    public BookAuthorLinkRepo() {
        db = DB.connectToDb();
    }

    public void addBookAuthorLink(Book newBook) {
        String query = "insert into book_author_link (bookId, authorId) values (?, ?)";
        try {
            // Must assume each book might have multiple authors
            // A book might have been entered without any authors, but that's fine.
            for (Author a : newBook.getAuthors()) {
                PreparedStatement sqlStatement = db.prepareStatement(query);
                sqlStatement.setInt(1, newBook.getBookId());
                sqlStatement.setInt(2, a.getAuthorId());
                sqlStatement.executeUpdate();
            }
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBookLink(Book modBook) {
        String query = "delete from book_author_link where bookId = ? and authorId = ?";
        try {
            for (Author a : modBook.getAuthors()) {
                PreparedStatement sqlStatement = db.prepareStatement(query);
                sqlStatement.setInt(1, modBook.getBookId());
                sqlStatement.setInt(2, a.getAuthorId());
                sqlStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // No updates for book links are necessary -- it is far more prudent to delete and add link records instead.
    // public void updateBookLink(Book newBook) {
    //     String query = "update (select * from author a inner join book_author_link bal " +
    //                     "on a.authorId = bal.authorId " +
    //                     "inner join book b on bal.bookId = b.bookId " +
    //                     "where b.bookId = ?) " +
    //                     "set bal.bookId = ?, bal.authorId = ? where a.authorId = ?";
    //     try {
    //         for (Author a : newBook.getAuthors()) {
    //             PreparedStatement sqlStatement = db.prepareStatement(query);
    //             sqlStatement.setInt(1, newBook.getBookId());
    //             sqlStatement.setInt(2, newBook.getBookId());
    //             sqlStatement.setInt(3, a.getAuthorId());
    //             sqlStatement.setInt(4, a.getAuthorId());
    //             sqlStatement.executeUpdate();
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
}
