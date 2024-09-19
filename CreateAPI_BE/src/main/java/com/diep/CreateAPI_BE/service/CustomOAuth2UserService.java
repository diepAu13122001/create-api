package com.diep.CreateAPI_BE.service;

import com.diep.CreateAPI_BE.entity.MyUser;
import com.diep.CreateAPI_BE.repository.UserRepository;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.AuthProvider;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        // Save or update the user in the database
        saveOrUpdateUser(convertToMyUser(oAuth2User, provider));
        return oAuth2User;
    }

    public MyUser convertToMyUser(OAuth2User principal, String provider) {
        Map<String, Object> attributes = principal.getAttributes();
        String id = attributes.containsKey("sub") ? (String) attributes.get("sub") : ((Integer) attributes.get("id")).toString();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        MyUser user = new MyUser();
        user.setName(name);
        user.setUid(id);
        user.setEmail(email);
        user.setProvider(provider);
        return user;
    }

    public MyUser saveOrUpdateUser(MyUser userConverter) {
        // Check if the user already exists
        Optional<MyUser> existingUser = userRepository.findById(userConverter.getUid()).stream().findFirst();
        MyUser user;
        if (existingUser.isEmpty()) {
            // check if different provider but duplicate email value
            Optional<MyUser> duplicateUser = userRepository.findAllByNameOrEmail(userConverter.getEmail()).stream().findFirst();
            if (duplicateUser.isEmpty()) {
                // If no user exists, create a new one
                user = userConverter;
                user.generateApiKey();
            } else {
                // update duplicate user -> no create more
                user = duplicateUser.get();
                user.setProvider(userConverter.getProvider()); // update latest login's provider
            }
        } else {
            // If user exists, update their information
            user = existingUser.get();
            user.setName(userConverter.getName());
            user.setEmail(userConverter.getEmail());
            user.setProvider(userConverter.getProvider());
        }
        // Save the user to the database
        return userRepository.save(user);
    }
}
