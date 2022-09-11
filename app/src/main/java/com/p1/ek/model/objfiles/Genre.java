package com.p1.ek.model.objfiles;

public class Genre {
    public Genre(int genreId, String genreName) {
        this.setGenreId(genreId);
        this.setGenreName(genreName);
    }
    
    public Genre() { this(0, ""); }

    public int getGenreId() { return genreId; }
    public void setGenreId(int genreId) { this.genreId = genreId; }

    public String getGenreName() { return genreName; }
    public void setGenreName(String genreName) { this.genreName = genreName; }

    private int genreId;
    private String genreName;
}
