package com.programming5.imdbproject.repository;

import com.programming5.imdbproject.domain.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {

    // magick here

}
