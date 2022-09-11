package com.p1.ek.model.repos;

import com.p1.ek.model.objfiles.Author;

// The repo class for authors
// BookRepo depends on this when adding new books
public class AuthorRepo {

    public Author getAuthorById(int authorId) {
        Author newAuthor = new Author();
        String query = "SELECT * FROM AUTHOR;";
        
        return newAuthor;
    }

    public void addAuthor(Author newAuthor) {
        
    }
    
}
