package com.programming5.imdbproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.programming5.imdbproject.domain.User;
import com.programming5.imdbproject.dto.AddDirectorDto;
import com.programming5.imdbproject.repository.DirectorRepository;
import com.programming5.imdbproject.repository.UserRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@AutoConfigureMockMvc
public class DirectorControllerTest {

    // Test for director controller to be here: ...
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "oleksii")
    void shouldShowDirectorDetailsIfAuthenticated() throws Exception {
        // Arrange
        Integer directorId = createDirectorAndGetIdAsUnknownEditor();

        var request = get("/directors/{id}", directorId);

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"))
                .andDo(print());
    }

    @Test
    void shouldNotShowDirectorDetailsIfAuthenticated() throws Exception {
        // Arrange
        Integer directorId = createDirectorAndGetIdAsUnknownEditor();

        var request = get("/directors/{id}", directorId);

        // Act
        var response = mockMvc.perform(request);

        // Assert
        response
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"))
                .andDo(print());

        directorRepository.deleteById(directorId);
        assertNull(directorRepository.findById(directorId).orElse(null));
        userRepository.delete(userRepository.findUserByUsername("john"));
        assertNull(userRepository.findUserByUsername("john"));
    }

    private Integer createDirectorAndGetIdAsUnknownEditor() throws Exception {
        var dto = new AddDirectorDto(
                "John",
                "Doe",
                LocalDate.of(1980, 8, 8),
                "Mexican",
                188.8D
        );

        userRepository.save(new User("john", "123", true));

        var request = post("/api/directors")
                .with(SecurityMockMvcRequestPostProcessors.user("john").password("123").roles("EDITOR"))
                .with(csrf());
        request.contentType(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        request.content(objectMapper.writeValueAsString(dto));
        var response = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();


        return JsonPath.read(response, "$.id");
    }

}
