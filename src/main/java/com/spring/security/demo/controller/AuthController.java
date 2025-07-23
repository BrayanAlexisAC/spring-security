package com.spring.security.demo.controller;

import com.spring.security.demo.dto.AuthLoginRequestDTO;
import com.spring.security.demo.dto.AuthResponseDTO;
import com.spring.security.demo.service.SSUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthController {

    private final SSUserDetailsService ssUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login (@RequestBody @Valid AuthLoginRequestDTO authLoginRequestDTO) {
        var login = ssUserDetailsService.loginUser(authLoginRequestDTO);
        return ResponseEntity.ok(login);
    }

    public AuthController(SSUserDetailsService ssUserDetailsService) {
        this.ssUserDetailsService = ssUserDetailsService;
    }
}
