package com.p1.ek.controller;
import java.util.List;

import com.p1.ek.model.objfiles.Book;
import com.p1.ek.model.objfiles.Genre;
import com.p1.ek.model.repos.GenreRepo;

public class GenreService {

    private GenreRepo gr;

    public GenreService() { gr = new GenreRepo(); }

    public List<Genre> readRecords() {
        return gr.getGenres();
    }
    public void createRecord(Book newBook) {
        gr.addGenres(newBook);
    }

    // Retrieve a genre from genre table by its id
    public Genre readRecordById(int id) {
        return gr.getGenreById(id);
    }

    // Retrieve all genres belonging to a book.
    public List<Genre> readRecordsForBook(Book aBook) {
        return gr.getGenresByBook(aBook);
    }

    public void updateRecord(Book modBook) {
        gr.updateGenresForBook(modBook);
    }
    
}
