package com.programming5.imdbproject.controller;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.service.DirectorService;
import com.programming5.imdbproject.viewmodel.DirectorViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorService directorService;
    private final ModelMapper modelMapper;

    public DirectorController(DirectorService directorService, ModelMapper modelMapper) {
        this.directorService = directorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/show")
    public String showDirectors(Model model) {

        List<Director> directors = directorService.getAll();

        List<DirectorViewModel> directorViewModels = directors
                .stream()
                .map(director -> modelMapper.map(director, DirectorViewModel.class))
                .toList();

        model.addAttribute("directors", directorViewModels);

        return "directors/show-directors";
    }

    @GetMapping("/{id}")
    public String showOneDirector(@PathVariable("id") Integer id, Model model) {

        Director director = directorService.getById(id);

        model.addAttribute("director", modelMapper.map(director, DirectorViewModel.class));

        return "/directors/single-director";
    }

    @GetMapping("/add")
    public String showForToAdd() {

        return "/directors/addDirector";
    }

    @GetMapping("/update/{id}")
    public String updateDirectorForm(@PathVariable("id") Integer id, Model model) {

        Director director = directorService.getById(id);

        model.addAttribute("director", modelMapper.map(director, DirectorViewModel.class));

        return "/directors/updateDirector";
    }

}
