package com.programming5.imdbproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public RedirectView index() {
        return new RedirectView("/directors/show");
    }
}
