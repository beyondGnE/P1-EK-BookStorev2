package com.p1.ek.model.objfiles;

public class BookAuthorLink {
    public BookAuthorLink(int bookId, int authorId) {
        this.setBookId(bookId);
        this.setAuthorId(authorId);
    }

    public BookAuthorLink() {
        this(0, 0);
    }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    private int bookId;
    private int authorId;
}
