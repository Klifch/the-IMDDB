package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Studio;
import com.programming5.imdbproject.repository.StudioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioServiceImpl implements StudioService {

    private final StudioRepository studioRepository;

    public StudioServiceImpl(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Override
    public List<Studio> getAll() {
        return studioRepository.findAll();
    }
}
