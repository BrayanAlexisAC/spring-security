package com.spring.security.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enable security by method annotations like @PreAuthorize (used into controllers)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Disable CSRF protection for simplicity, Use disble for stateless APIs, for stateful applications keep enabled
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .authorizeHttpRequests(authorizationRequests ->
//                        authorizationRequests
//                                .requestMatchers(HttpMethod.GET, "/helloNoSecure").permitAll() // Allow public access to /helloNoSecure
//                                .requestMatchers(HttpMethod.GET,"/api/student/scores/average/**").hasAuthority("READ") // Allow access to average scores for users with READ authority
//                                .anyRequest().authenticated()); // All other requests require authentication

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Method to encode passwords
        return NoOpPasswordEncoder.getInstance(); // NO SONAR Just for demonstration purposes, do not use in production!
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Method to get user details from a database or other source
        var userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.add(User.withUsername("powerfulUser")
                .password("powerfulUser") // NO SONAR
                // Roles deleted because, it was not working with hasRole method
                .authorities("ROLE_ADMIN", "CREATE", "READ", "UPDATE", "DELETE")
                .build());

        userDetailsList.add(User.withUsername("readUser")
                .password("readUser")
                .authorities("ROLE_USER", "READ")
                .build());

        userDetailsList.add(User.withUsername("updateUser")
                .password("updateUser")
                .authorities("ROLE_USER", "UPDATE")
                .build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
}
