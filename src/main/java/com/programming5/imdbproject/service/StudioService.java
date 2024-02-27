package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Studio;

import java.util.List;

public interface StudioService {

    List<Studio> getAll();

    Studio getById(Integer id);

}
