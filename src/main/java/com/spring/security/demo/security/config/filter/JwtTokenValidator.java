package com.spring.security.demo.security.config.filter;

import com.spring.security.demo.utils.JwtUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtTokenValidator extends OncePerRequestFilter {   // extends OncePerRequestFilter to ensure the filter is executed once per request

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token)){
            token = token.replace("Bearer ", "");
            var username = jwtUtils.extractUsername(token);
            var authorities = jwtUtils.getClaim(token, "authorities");
            var authoritiesCollection =  AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
            var securityContext = SecurityContextHolder.getContext();
            var userAuthenticated = new UsernamePasswordAuthenticationToken(
                    username,
                    null, // credentials are not needed for JWT validation
                    authoritiesCollection
            );
            securityContext.setAuthentication(userAuthenticated);
            // Security context holder is added as reference, ensure this is working, in the other hand, added to security holder manually
        }

        filterChain.doFilter(request, response);
    }

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
}
