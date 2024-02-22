package com.programming5.imdbproject.repository;

import com.programming5.imdbproject.domain.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Integer> {

    // magick here

}
