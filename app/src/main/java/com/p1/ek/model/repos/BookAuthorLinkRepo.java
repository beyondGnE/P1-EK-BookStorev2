package com.p1.ek.model.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.p1.ek.model.dbconn.DB;
import com.p1.ek.model.objfiles.Book;
import com.p1.ek.model.objfiles.BookAuthorLink;
import com.p1.ek.model.objfiles.Author;

public class BookAuthorLinkRepo {
    private Connection db;
    private BookRepo br;
    private AuthorRepo ar;

    public BookAuthorLinkRepo() {
        db = DB.connectToDb();
        br = new BookRepo();
        ar = new AuthorRepo();
    }

    public void addBookAuthorLink(Book newBook) { // Cannot get ids from the book itself!
        System.out.println(newBook);
        Book gotBook = br.getBookByTitle(newBook.getTitle());
        String query = "insert into book_author_link (bookId, authorId) values (?, ?)";
        try {
            // Must assume each book might have multiple authors
            // A book might have been entered without any authors, but that's fine.
            // if (gotAuthors != null) {
                for (Author a : newBook.getAuthors()) {
                    PreparedStatement sqlStatement = db.prepareStatement(query);
                    sqlStatement.setInt(1, gotBook.getBookId());
                    sqlStatement.setInt(2, ar.getAuthorByTitle(a.getFirstName(), a.getLastName()).getAuthorId());
                    System.out.println(a.getAuthorId());
                    sqlStatement.executeUpdate();
                }
            // }
        
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BookAuthorLink getBALink(Book book) {
        String query = "select * from book_author_link where bookId = ?";
        try {
            PreparedStatement sql = db.prepareStatement(query);
            sql.setInt(1, book.getBookId());
            ResultSet rs = sql.executeQuery();
            if (rs.next()) {
                return new BookAuthorLink(rs.getInt("bookId"), rs.getInt("authorId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteBookLink(Book modBook) {
        if (this.getBALink(modBook) != null) { // Only if the bookId already exists in the table
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
