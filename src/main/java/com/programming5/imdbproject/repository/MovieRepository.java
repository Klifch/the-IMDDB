package com.programming5.imdbproject.repository;

import com.programming5.imdbproject.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    // magick here

    @Query("SELECT m FROM Movie m WHERE LOWER(m.movieName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Movie> findByMovieNameContainingIgnoreCase(@Param("searchTerm") String searchTerm);

}
