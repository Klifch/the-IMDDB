package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getById(Integer id) {
        return movieRepository.findById(id).orElse(null);
    }


}
