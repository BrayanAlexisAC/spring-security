package com.spring.security.demo.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotBlank;

@JsonPropertyOrder({
        "username",
        "password"
})
public record AuthLoginRequestDTO (@NotBlank String username, @NotBlank String password) {

    // Compact constructor to validate the fields
    // with annotations @NotBlank this is not needed, but it is a good practice to validate the fields in the constructor
    public AuthLoginRequestDTO {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Username cannot be null or empty value");
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Password cannot be null or empty value");
        }
    }
}
