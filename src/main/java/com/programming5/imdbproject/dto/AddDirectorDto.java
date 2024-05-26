package com.programming5.imdbproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AddDirectorDto(

        @NotEmpty
        @Size(min = 3, max = 10)
        String firstName,

        @NotEmpty
        @Size(min = 3, max = 10)
        String lastName,

        @NotNull
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dateOfBirth,

        String nationality,

        @Min(100)
        @Max(250)
        Double height

) {
}
