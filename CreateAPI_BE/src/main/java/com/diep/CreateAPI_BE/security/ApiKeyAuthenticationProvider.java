package com.diep.CreateAPI_BE.security;

import com.diep.CreateAPI_BE.entity.MyUser;
import com.diep.CreateAPI_BE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;  // Inject your repository

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apiKey = (String) authentication.getPrincipal();
        MyUser user = userRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid API Key"));

        // Create an authentication token based on the user
        return new ApiKeyAuthenticationToken(user, apiKey);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
