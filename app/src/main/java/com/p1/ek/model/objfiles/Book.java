package com.p1.ek.model.objfiles;

public class Book extends Item {

    public Book(String title, double price, int quantity, String imgUrl, String isbn, int authorId, int bookId) {
        super(title, price, quantity, imgUrl);
        this.setIsbn(isbn);
        this.setAuthorId(authorId);
        this.setBookId(bookId);
    }

    public Book() { this("", 0, 0, "","", 0, 0); }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre;}

    private String isbn;
    private int authorId;
    private int bookId; // primary key
    private String genre;
}
