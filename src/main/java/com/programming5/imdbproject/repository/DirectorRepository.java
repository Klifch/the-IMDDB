package com.programming5.imdbproject.repository;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {

    // magick here

}
