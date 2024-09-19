package com.diep.CreateAPI_BE.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final List<String> PROTECTED_URLS = List.of("/collections", "/objects");


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String apiKey = request.getHeader(API_KEY_HEADER);

        if (PROTECTED_URLS.stream().anyMatch(request.getRequestURI()::startsWith) && (apiKey == null || apiKey.isEmpty())) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API Key is missing");
            return;
        }

        PreAuthenticatedAuthenticationToken authRequest = new PreAuthenticatedAuthenticationToken(apiKey, null);
        // Set the authentication in SecurityContextHolder if you want
        // SecurityContextHolder.getContext().setAuthentication(authRequest);

        filterChain.doFilter(request, response);
    }
}