package com.p1.ek.model.objfiles;

public class BookGenreLink {
    public BookGenreLink(int bookId, int genreId) {
        this.setBookId(bookId);
        this.setGenreId(genreId);
    }

    public BookGenreLink() {
        this(0, 0);
    }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getGenreId() { return genreId; }
    public void setGenreId(int genreId) { this.genreId = genreId; }

    private int bookId;
    private int genreId;
}
