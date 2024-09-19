package com.diep.CreateAPI_BE.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;


@Component
public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//        OAuth2User oauthUser = oauthToken.getPrincipal();
//
//        // Retrieve the OAuth2AuthorizedClient using the registration ID and the authentication
//        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
//                oauthToken.getAuthorizedClientRegistrationId(),
//                oauthToken.getName()
//        );
//
//        String tokenValue = authorizedClient.getAccessToken().getTokenValue();
//
//
//        // Send the OAuth2 access token in the response (JSON format)
//        response.setContentType("application/json");
//        response.getWriter().write("{\"accessToken\": \"" + tokenValue + "\"}");
//        response.getWriter().flush();
        response.sendRedirect("/users/me");
    }
}
