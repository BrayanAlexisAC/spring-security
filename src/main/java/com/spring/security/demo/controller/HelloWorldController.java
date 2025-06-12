package com.spring.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/helloSecure")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/helloNoSecure")
    public String helloNoSecure() {
        return "Hello, World! No security here.";
    }
}
