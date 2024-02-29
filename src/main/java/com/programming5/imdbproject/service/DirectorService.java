package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;

import java.util.List;

public interface DirectorService {

    List<Director> getAll();

    Director getById(Integer id);

    List<Movie> getMoviesForDirectorById(Integer id);

    void delete(Integer id);

}
