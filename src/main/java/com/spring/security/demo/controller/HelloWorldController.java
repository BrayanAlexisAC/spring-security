package com.spring.security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("denyAll()") // Deny all access by default
public class HelloWorldController {

    @GetMapping("/helloSecure")
    @PreAuthorize("hasAuthority('READ')")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/helloNoSecure")
    @PreAuthorize("permitAll()") // Allow all access to this endpoint
    public String helloNoSecure() {
        return "Hello, World! No security here.";
    }
}
