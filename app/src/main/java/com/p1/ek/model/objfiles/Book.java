package com.p1.ek.model.objfiles;

import java.util.ArrayList;
import java.util.List;

public class Book extends Item {

    public Book(int bookId, String title, double price, int quantity, //String genre, // Get genre through the link table!
                String imgUrl, String isbn, String publishDate, List<Author> authors, List<Genre> genres) {
        super(title, price, quantity, imgUrl);
        this.setIsbn(isbn);
        this.setBookId(bookId);
        this.setPublishDate(publishDate);

        this.authors = authors;
        this.genres = genres;
        // this.setGenre(genre);
    }

    public Book() { this(0, "", 0, 0,"","","01/01/1970", new ArrayList<>(), new ArrayList<>()); }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    // public int getAuthorId() { return authorId; }
    // public void setAuthorId(int authorId) { this.authorId = authorId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    // public String getGenre() { return genre; }
    // public void setGenre(String genre) { this.genre = genre;}

    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

    public List<Author> getAuthors() { return authors; }
    public List<Genre> getGenres() { return genres; }

    private String isbn;
    private List<Author> authors;
    private int bookId; // primary key
    private List<Genre> genres;
    private String publishDate;
}
