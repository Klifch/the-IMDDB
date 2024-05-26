package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface DirectorService {

    List<Director> getAll();

    Director getById(Integer id);

    List<Movie> getMoviesForDirectorById(Integer id);

    void delete(Integer id);

    Director add(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String nationality,
            Double height,
            User creator
    );

    Director patch(
            Integer id,
            String firstName,
            String lastName,
            LocalDate birthdate,
            String nationality,
            Double height
    );

    Boolean canUserModify(String username, Integer id);

    Boolean alreadyExists(String firstname, String lastname, LocalDate dateOfBirth);

}
