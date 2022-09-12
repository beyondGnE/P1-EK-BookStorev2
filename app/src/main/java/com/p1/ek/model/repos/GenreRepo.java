package com.p1.ek.model.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.p1.ek.model.dbconn.DB;
import com.p1.ek.model.objfiles.Genre;

public class GenreRepo {
    private Connection db;

    public GenreRepo() {
        db = DB.connectToDb();
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
                bookGenres.add(new Genre(rs.getInt("genreId"),
                                            rs.getString("genreName")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return bookGenres;
    }
}
