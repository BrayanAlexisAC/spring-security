package com.spring.security.demo.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// keyword record was introduced in Java 14 as a preview feature and became a standard feature in Java 16.
// Records are a special kind of class in Java that are used to create immutable data objects with less boilerplate (repetitive code) code.
@JsonPropertyOrder({
        "username",
        "message",
        "jwt",
        "status"
})
public record AuthResponseDTO (String username, String message, String jwt, boolean status) {

    public AuthResponseDTO(String username, String message, String jwt) {
        this(username, message, jwt, true);
    }

    public AuthResponseDTO(String username, String message) {
        this(username, message, null);
    }
}
