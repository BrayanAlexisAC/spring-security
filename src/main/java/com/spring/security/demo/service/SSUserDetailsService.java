package com.spring.security.demo.service;

import com.spring.security.demo.dto.AuthLoginRequestDTO;
import com.spring.security.demo.dto.AuthResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SSUserDetailsService extends UserDetailsService {
    /**
     * Method to log in a user with username and password.
     *
     * @param authLoginRequestDTO the login request containing username and password
     * @return AuthResponseDTO containing the response details after login
     */
    AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequestDTO);
}
