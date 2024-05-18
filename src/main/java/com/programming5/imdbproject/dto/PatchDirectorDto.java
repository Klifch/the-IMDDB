package com.programming5.imdbproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatchDirectorDto(
        @Size(min = 3, max = 10)
        String firstName,
        @Size(min = 3, max = 10)
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth,

        String nationality,
        @Min(100)
        @Max(250)
        Double height
) {

}

