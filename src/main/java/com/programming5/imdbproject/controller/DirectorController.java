package com.programming5.imdbproject.controller;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.service.DirectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/show")
    public String showDirectors(Model model) {

        List<Director> directors = directorService.getAll();

        model.addAttribute("directors", directors);

        return "directors/show-directors";
    }

    @GetMapping("/{id}")
    public String showOneDirector(@PathVariable("id") Integer id, Model model) {

        // TODO: should return DTO to not expose the director
        Director director = directorService.getById(id);

        model.addAttribute("director", director);

        return "/directors/single-director";
    }

}
