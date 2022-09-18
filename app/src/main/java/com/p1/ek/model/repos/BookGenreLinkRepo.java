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
}
