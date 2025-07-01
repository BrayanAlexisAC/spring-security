package com.spring.security.demo.service.impl;

import com.spring.security.demo.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

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

    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
