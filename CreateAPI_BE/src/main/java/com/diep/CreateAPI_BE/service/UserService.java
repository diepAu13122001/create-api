package com.diep.CreateAPI_BE.service;

import com.diep.CreateAPI_BE.dto.UserDto;
import com.diep.CreateAPI_BE.entity.MyCollection;
import com.diep.CreateAPI_BE.entity.MyUser;
import com.diep.CreateAPI_BE.repository.CollectionRepository;
import com.diep.CreateAPI_BE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    // Get collections by API key
    public Set<MyCollection> getCollectionsByApiKey(String apiKey) {
        return this.getUserByApiKey(apiKey).getCollections();
    }

    // update collection
    public MyUser updateCollectionList(String apiKey, Set<MyCollection> collections) {
        MyUser user = this.getUserByApiKey(apiKey);
        user.setCollections(collections);
        return this.userRepository.save(user);
    }

    // Get user by API key
    public MyUser getUserByApiKey(String apiKey) {
        return this.userRepository.findByApiKey(apiKey).get();
    }

    public List<UserDto> searchUser(String query) {
        if (query == null) {
            // find all
//            return this.userRepository.findAll();
        } else {
            // find by key word
//            return this.userRepository.findAllByNameOrEmail(query);
        }
        return null;
    }

    public UserDto updateUser(UserDto userDto) {
        return userDto;
    }

    public MyUser getUser(String uid, String email) {
        return this.userRepository.findById(uid).isEmpty() ? this.userRepository.findByEmail(email) : this.userRepository.findById(uid).get();
    }

    public MyUser convertToEntity(UserDto userDto) {
        return null;
    }

    public UserDto convertToDto(MyUser user) {
        return null;
    }
}
