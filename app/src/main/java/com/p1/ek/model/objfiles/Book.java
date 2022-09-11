package com.p1.ek.model.objfiles;

public class Book extends Item {

    public Book(int bookId, String title, double price, int quantity, //String genre, // Get genre through the link table!
                String imgUrl, String isbn, String publishDate) {
        super(title, price, quantity, imgUrl);
        this.setIsbn(isbn);
        this.setBookId(bookId);
        this.setPublishDate(publishDate);
        // this.setGenre(genre);
    }

    public Book() { this(0, "", 0, 0,"","","01/01/1970"); }

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

    private String isbn;
    // private int authorId;
    private int bookId; // primary key
    // private String genre;
    private String publishDate;
}
