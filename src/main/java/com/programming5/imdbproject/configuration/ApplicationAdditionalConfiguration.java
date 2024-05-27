package com.programming5.imdbproject.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableCaching
public class ApplicationAdditionalConfiguration {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
