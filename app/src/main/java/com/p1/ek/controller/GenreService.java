package com.p1.ek.controller;
import java.util.List;

import com.p1.ek.model.objfiles.Genre;
import com.p1.ek.model.repos.GenreRepo;

public class GenreService {

    private GenreRepo gr;

    public GenreService() { gr = new GenreRepo(); }

    public List<Genre> readRecords() {
        return gr.getGenres();
    }
    public void createRecord(Genre genre) {
        
    }

    public Genre readRecordById(int id) {
        return gr.getGenreById(id);
    }

    // public List<Genre>
    
}
