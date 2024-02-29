package com.programming5.imdbproject.dto;

import com.programming5.imdbproject.domain.Movie;

public record MovieDto(Integer id, String name) {

    public static MovieDto fromDomain(Movie movie) {
        return new MovieDto(movie.getMovieId(), movie.getMovieName());
    }
}
