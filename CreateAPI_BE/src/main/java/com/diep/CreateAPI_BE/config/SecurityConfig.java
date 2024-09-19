package com.diep.CreateAPI_BE.config;

import com.diep.CreateAPI_BE.security.ApiKeyAuthenticationProvider;
import com.diep.CreateAPI_BE.security.ApiKeyFilter;
import com.diep.CreateAPI_BE.security.CustomOAuth2AuthenticationSuccessHandler;
import com.diep.CreateAPI_BE.service.CustomOAuth2UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomOAuth2UserService oauthUserService;
    @Autowired
    private ApiKeyFilter apiKeyFilter;
    @Autowired
    private ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;
    @Autowired
    private CustomOAuth2AuthenticationSuccessHandler successHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(s -> s.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/**").permitAll();
                    auth.anyRequest().authenticated();
                }).oauth2Login(oauth2 -> oauth2
//                        .successHandler(successHandler)
                                .defaultSuccessUrl("/users/me")
                                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(this.oauthUserService))
                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)  // Add API key filter for API requests
                .authenticationProvider(apiKeyAuthenticationProvider) // Use custom API key provider
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
