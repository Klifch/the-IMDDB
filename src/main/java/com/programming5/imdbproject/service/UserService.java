package com.programming5.imdbproject.service;

import com.programming5.imdbproject.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);
}
