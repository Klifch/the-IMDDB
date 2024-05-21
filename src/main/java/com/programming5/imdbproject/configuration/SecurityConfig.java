package com.programming5.imdbproject.configuration;

import com.programming5.imdbproject.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    /*
        Documentation reference:
    https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/dao-authentication-provider.html
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationSuccessHandler customAuthenticationSuccessHandler
    ) throws Exception {
        http.authorizeHttpRequests(authorize -> {
            authorize
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/movies/show").permitAll()
                    .requestMatchers("/directors/show").permitAll()
                    .requestMatchers("/studios/show").permitAll()
                    .requestMatchers(
                            antMatcher(HttpMethod.GET, "/js/**"),
                            antMatcher(HttpMethod.GET, "/css/**"),
                            antMatcher(HttpMethod.GET, "/webjars/**")
                    ).permitAll()
                    .requestMatchers("/directors/add").hasAnyRole("EDITOR", "ADMIN")
                    .requestMatchers("/directors/update/**").hasAnyRole("EDITOR", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/directors").hasAnyRole("EDITOR", "ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/directors/**").hasAnyRole("EDITOR", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/directors/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated();
        });

        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
                (request, response, ex) -> {
                    if (request.getRequestURI().startsWith("/api")) {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                    } else {
                        response.sendRedirect(request.getContextPath() + "/login");
                    }
                }
        ));
        http.formLogin((login) -> login
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .permitAll()
        );


        http.logout(logout -> logout.logoutSuccessUrl("/"));

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 10 rounds by default
    }

}
