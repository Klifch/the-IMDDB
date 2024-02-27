package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.repository.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public List<Director> getAll() {
        return directorRepository.findAll();
    }

    @Override
    public Director getById(Integer id) {
        return directorRepository.findById(id).orElse(null);
    }


}
