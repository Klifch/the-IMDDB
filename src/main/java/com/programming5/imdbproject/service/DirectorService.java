package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;

import java.time.LocalDate;
import java.util.List;

public interface DirectorService {

    List<Director> getAll();

    Director getById(Integer id);

    List<Movie> getMoviesForDirectorById(Integer id);

    void delete(Integer id);

    Director add(String firstName, String lastName, LocalDate dateOfBirth);
}
