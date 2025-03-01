package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAll();

    Movie getById(Integer id);

    List<Movie> search(String searchTerm);

}
