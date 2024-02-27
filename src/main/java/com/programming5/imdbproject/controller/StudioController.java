package com.programming5.imdbproject.controller;

import com.programming5.imdbproject.domain.Movie;
import com.programming5.imdbproject.domain.Studio;
import com.programming5.imdbproject.service.StudioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/studios")
public class StudioController {

    private final StudioService studioService;

    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    @GetMapping("/show")
    public String showStudios(Model model) {

        List<Studio> studios = studioService.getAll();

        model.addAttribute("studios", studios);

        return "studios/show-studios";
    }

    @GetMapping("/{id}")
    public String showOneStudio(@PathVariable("id") Integer id, Model model) {

        // TODO: should return DTO to not expose the movie
        Studio studio = studioService.getById(id);

        model.addAttribute("studio", studio);

        return "/studios/single-studio";
    }
}
