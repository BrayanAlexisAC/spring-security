package com.spring.security.demo.service.impl;

import com.spring.security.demo.constants.Constants;
import com.spring.security.demo.dto.AuthLoginRequestDTO;
import com.spring.security.demo.dto.AuthResponseDTO;
import com.spring.security.demo.repository.UserRepository;
import com.spring.security.demo.service.SSUserDetailsService;
import com.spring.security.demo.utils.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserDetailsServiceImp implements SSUserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get user by Username
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Create roles using GrantedAuthority
        var lstGrantedAuthority = new ArrayList<SimpleGrantedAuthority>();

        user.getLstRoles().forEach(role ->
                lstGrantedAuthority.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

        // Add Permissions to roles into the User
        user.getLstRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> lstGrantedAuthority.add(new SimpleGrantedAuthority(permission.getName())));

        // Return a new User Spring Security Object
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                lstGrantedAuthority
        );
    }

    @Override
    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequestDTO) {
        var username = authLoginRequestDTO.username();
        var password = authLoginRequestDTO.password();
        var authenticated = authenticated(username, password);
        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticated);

        return new AuthResponseDTO(username, "success", jwtUtils.createToken(authenticated));
    }

    public Authentication authenticated(String username, String password) {
        var userDetails = loadUserByUsername(username);
        if (Objects.isNull(userDetails)){
            throw new BadCredentialsException(Constants.INVALID_USERNAME_OR_PASSWORD);
        } else if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException(Constants.INVALID_USERNAME_OR_PASSWORD);
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public UserDetailsServiceImp(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
}
