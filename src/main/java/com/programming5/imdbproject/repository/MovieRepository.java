package com.programming5.imdbproject.repository;

import com.programming5.imdbproject.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    // magick here

}
