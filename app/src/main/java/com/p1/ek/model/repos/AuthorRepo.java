package com.p1.ek.model.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.p1.ek.model.dbconn.DB;
import com.p1.ek.model.objfiles.Author;

// The repo class for authors
// BookRepo depends on this when adding new books
public class AuthorRepo {

    private Connection db;

    public AuthorRepo() { db = DB.connectToDb(); }

    public List<Author> getAuthors() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM AUTHOR;";
        try {
            Statement sqlStatement = db.createStatement();
            ResultSet rs = sqlStatement.executeQuery(query);

            while (rs.next()) {
                authors.add(new Author(
                    rs.getInt("authorId"),
                    rs.getString("firstName"),
                    rs.getString("lastName")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Author getAuthorById(int authorId) {
        
        String query = "SELECT * FROM AUTHOR where authorId = ?;";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setInt(1, authorId);
            ResultSet rs = sqlStatement.executeQuery();
            if (rs != null) {
                Author getAuthor = new Author(rs.getInt("authorId"), 
                                    rs.getString("firstName"), rs.getString("lastName"));
                return getAuthor;
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Author getAuthorByTitle(String firstName, String lastName) {
        
        String query = "SELECT * FROM AUTHOR where firstName = ? and lastName = ?;";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setString(1, firstName);
            sqlStatement.setString(2, lastName);
            ResultSet rs = sqlStatement.executeQuery();
            if (rs != null) {
                Author getAuthor = new Author(rs.getInt("authorId"), 
                                    rs.getString("firstName"), rs.getString("lastName"));
                return getAuthor;
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAuthor(Author newAuthor) {
        Author possAuthor = this.getAuthorByTitle(newAuthor.getFirstName(), newAuthor.getLastName()); 
        // User more likely to identify authors by name rather than by id.
        
        if (possAuthor == null) { // Check if exists. If it doesn't, add; otherwise do nothing.
            String query = "insert into author(firstName, lastName) " +
                            "values (?, ?)";
            try {
                PreparedStatement sqlStatement = db.prepareStatement(query);
                sqlStatement.setString(1, newAuthor.getFirstName());
                sqlStatement.setString(2, newAuthor.getLastName());
                sqlStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addAuthors(List<Author> newAuthors) {
        for (Author a : newAuthors) {
            this.addAuthor(a);
        }
    }
    
    // Special method
    public List<Author> getAuthorsByBookId(int bookId) {
        List<Author> bookAuthors = new ArrayList<>();
        String query = "select * from author a inner join book_author_link bal " +
                        "on a.authorId = bal.authorId " +
                        "inner join book b on bal.bookId = b.bookId " +
                        "where b.bookId = ?";
        try {
            PreparedStatement sqlStatement = db.prepareStatement(query);
            sqlStatement.setInt(1,bookId);
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next()) {
                bookAuthors.add(new Author(rs.getInt("authorId"),
                                            rs.getString("firstName"),
                                            rs.getString("lastName")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return bookAuthors;
    }

    public int findAuthorMaxId() {
        String query = "select * from author where authorId = (select max(authorId) from author);";
        int max = -1;
        try {
            Statement sqlStatement = db.createStatement();
            ResultSet rs = sqlStatement.executeQuery(query);
            if (rs.next()) {
                max = rs.getInt("authorId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return max;
    }
}
