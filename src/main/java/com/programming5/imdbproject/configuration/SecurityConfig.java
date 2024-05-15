package com.programming5.imdbproject.configuration;

import com.programming5.imdbproject.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
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
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
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
                    .anyRequest().authenticated();
        });
        http.csrf(csrf -> csrf.disable());
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
                (request, response, ex) -> {
                    if (request.getRequestURI().startsWith("/api")) {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                    } else {
                        response.sendRedirect(request.getContextPath() + "/login");
                    }
                }
        ));
        http.formLogin(login -> login.permitAll().loginPage("/login"));
        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
