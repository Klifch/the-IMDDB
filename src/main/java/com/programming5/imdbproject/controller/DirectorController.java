package com.programming5.imdbproject.controller;

import com.programming5.imdbproject.domain.Director;
import com.programming5.imdbproject.domain.User;
import com.programming5.imdbproject.security.CustomUserDetails;
import com.programming5.imdbproject.service.DirectorService;
import com.programming5.imdbproject.viewmodel.DirectorViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
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
    public String showDirectors(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<Director> directors = directorService.getAll();

        List<DirectorViewModel> directorViewModels = directors
                .stream()
                .map(director -> {
                    boolean canModify;
                    if (userDetails != null) {
                        boolean isCreator = directorService.canUserModify(userDetails.getUsername(), director.getDirectorId());
                        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(
                                role -> role.getAuthority().equals("ROLE_ADMIN")
                        );
                        canModify = isCreator || isAdmin;
                    } else {
                        canModify = false;
                    }
                    return DirectorViewModel.from(director, canModify);
                }).toList();

        model.addAttribute("directors", directorViewModels);

        return "directors/show-directors";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showOneDirector(@PathVariable("id") Integer id, Model model) {

        Director director = directorService.getById(id);

        if (director == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Director not found!");
        }

        model.addAttribute("director", modelMapper.map(director, DirectorViewModel.class));
        model.addAttribute("author", director.getCreator().getUsername());

        return "/directors/single-director";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public String showForToAdd() {

        return "/directors/addDirector";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_EDITOR') and @directorServiceImpl.canUserModify(principal.username, #id) or hasRole('ROLE_ADMIN')")
    public String updateDirectorForm(@PathVariable("id") Integer id, Model model) {

        Director director = directorService.getById(id);

        if (director == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Director not found!");
        }

        model.addAttribute("director", modelMapper.map(director, DirectorViewModel.class));

        return "/directors/updateDirector";
    }

    @GetMapping("/import")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showImportDirectors(Model model) {

        return "directors/importDirectors";
    }

    @PostMapping("/import")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String importDirectorsCSV(
            @RequestParam("directors_csv") MultipartFile file,
            @AuthenticationPrincipal UserDetails user,
            Model model
    ) throws IOException {
        System.out.println(file.getOriginalFilename());
        System.out.println(user.getUsername());

        String fileName = file.getOriginalFilename();

        if (fileName != null && fileName.toLowerCase().endsWith(".csv")) {
            model.addAttribute("wrongFile", false);
            model.addAttribute("inProgress", true);

            directorService.handleImport(file.getInputStream(), user.getUsername());
        } else {
            model.addAttribute("wrongFile", true);
        }

        return "directors/importDirectors";
    }

}
