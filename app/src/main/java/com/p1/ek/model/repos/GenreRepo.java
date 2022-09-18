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
import com.p1.ek.model.objfiles.Genre;

public class GenreRepo {
    private Connection db;

    public GenreRepo() {
        db = DB.connectToDb();
    }

    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        String query = "SELECT * FROM genre;";
        try {
            Statement sqlStatement = db.createStatement();
            ResultSet rs = sqlStatement.executeQuery(query);

            while (rs.next()) {
                // For a single book, get list of authors and genres
                genres.add(new Genre(rs.getInt("genreId"),
                            rs.getString("genreName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public Genre getGenreById(int id) {
        String query = "select * from genre where genreId = ?";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setInt(1, id);
            ResultSet rs = sqlStatement.executeQuery();
            if (rs != null) {
                Genre getGenre = new Genre(rs.getInt("genreId"), rs.getString("genreName"));
                return getGenre;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Genre getGenreByName(String genreName) {
        String query = "select * from genre where genreName = ?";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setString(1, genreName);
            ResultSet rs = sqlStatement.executeQuery();
            if (rs != null) {
                Genre getGenre = new Genre(rs.getInt("genreId"), rs.getString("genreName"));
                return getGenre;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Genre> getGenresByBookId(int bookId) {
        List<Genre> bookGenres = new ArrayList<>();
        String query = "select * from genre g inner join book_genre_link bgl " +
                        "on g.genreId = bgl.genreId " +
                        "inner join book b on bgl.bookId = b.bookId " +
                        "where b.bookId = ?";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setInt(1,bookId);
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next()) {
                bookGenres.add(new Genre(rs.getInt("genreId"),rs.getString("genreName")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return bookGenres;
    }
    
    // Called when retrieving a book.
    public List<Genre> getGenresByBook(Book aBook) {
        List<Genre> bookGenres = new ArrayList<>();
        String query = "select * from genre g inner join book_genre_link bgl " +
                        "on g.genreId = bgl.genreId " +
                        "inner join book b on bgl.bookId = b.bookId " +
                        "where b.bookId = ?";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setInt(1,aBook.getBookId());
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next()) {
                bookGenres.add(new Genre(rs.getInt("genreId"),rs.getString("genreName")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return bookGenres;
    }

    public void addGenre(Genre newGenre) {
        Genre possGenre = this.getGenreByName(newGenre.getGenreName());
        
        if (possGenre == null) { // Check if exists. If it doesn't, add; otherwise do nothing.
            String query = "insert into author(genreName) " +
                            "values (?)";
            try {
                PreparedStatement sqlStatement = db.prepareStatement(query);
                sqlStatement.setString(1, newGenre.getGenreName());
                sqlStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addGenres(Book newBook) {
        for (Genre g : newBook.getGenres()) {
            this.addGenre(g);
        }
    }

    public void updateGenresForBook(Book modBook) {
        String query = "update (select * from genre g inner join book_genre_link bgl " +
                        "on g.genreId = bgl.genreId " +
                        "inner join book b on bgl.bookId = b.bookId " +
                        "where b.bookId = ?) " +
                        "set genreName = ? where genreId = ?";
        try {
            for (Genre g : modBook.getGenres()) {
                PreparedStatement sqlStatement = db.prepareStatement(query);
                sqlStatement.setInt(1, modBook.getBookId());
                sqlStatement.setString(2, g.getGenreName());
                sqlStatement.setInt(3, g.getGenreId());
                sqlStatement.executeUpdate();
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
