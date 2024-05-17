package com.programming5.imdbproject.security;

import com.programming5.imdbproject.domain.User;
import com.programming5.imdbproject.dto.WebUser;
import com.programming5.imdbproject.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;


// basically "what we want to do if the authentication is successful?"
// I am using userDetails for accessing user info on webpages, but I thought it's fun to have it in the session as well

@Controller
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public CustomAuthSuccessHandler(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        String username = authentication.getName();
        User user = userService.findByUsername(username);

        HttpSession session = request.getSession();
        session.setAttribute("user", modelMapper.map(user, WebUser.class));
    }
}
