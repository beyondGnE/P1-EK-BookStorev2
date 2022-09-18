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
}
