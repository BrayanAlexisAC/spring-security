package com.spring.security.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(autorized ->
                                autorized.requestMatchers("/helloNoSecure").permitAll()
//                                      .requestMatchers("/public/**").permitAll() // Allow access to all endpoints starting with /public/
                                        .anyRequest().authenticated() // All other requests require authentication
                ).formLogin(AbstractAuthenticationFilterConfigurer::permitAll).httpBasic(Customizer.withDefaults()); // Use HTTP Basic authentication

        return httpSecurity.build();
    }

}
