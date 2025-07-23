package com.spring.security.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.private.key}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken(Authentication authentication) {
        var algorithm = Algorithm.HMAC512(privateKey);
        var username = authentication.getPrincipal().toString();
        var authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(userGenerator)  // user that will create the token
                .withSubject(username)  // user that will be authenticated
                .withClaim("authorities", authorities)  // user authorities
                .withIssuedAt(new Date())   // token creation date
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))  // token expiration date (1 hour expiration)
                .withJWTId(UUID.randomUUID().toString())    // unique identifier for the token
                .withNotBefore(new Date())  // valid token after creation (today)
                .sign(algorithm);   // sign the token with the algorithm
    }

    public DecodedJWT validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC512(privateKey);
            var verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)  // user that will create the token
                    .build();

            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid JWT token");
        }
    }

    public String extractUsername(String token) {
        var decodedJWT = validateToken(token);
        return decodedJWT.getSubject();
    }

    public String getClaim(String token, String claimName) {
        var decodedJWT = validateToken(token);
        return decodedJWT.getClaim(claimName).asString();
    }

    public Map<String, Claim> getClaims(String token) {
        var decodedJWT = validateToken(token);
        return decodedJWT.getClaims();
    }

}
