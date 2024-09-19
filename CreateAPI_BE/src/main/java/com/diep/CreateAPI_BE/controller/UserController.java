package com.diep.CreateAPI_BE.controller;

import com.diep.CreateAPI_BE.dto.UserDto;
import com.diep.CreateAPI_BE.entity.MyCollection;
import com.diep.CreateAPI_BE.entity.MyUser;
import com.diep.CreateAPI_BE.service.CustomOAuth2UserService;
import com.diep.CreateAPI_BE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    //    create ------------------------------------------
    //    read ------------------------------------------
    @GetMapping("/me")
    public MyUser getUser(@AuthenticationPrincipal OAuth2User principal) {
        MyUser user = this.customOAuth2UserService.convertToMyUser(principal, "");
        return this.userService.getUser(user.getUid(), user.getEmail());
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return this.userService.searchUser(null);
    }

    @GetMapping(value = "/search", params = "query")
    public List<UserDto> searchByKeyword(@RequestParam(required = true) String query) {
        return this.userService.searchUser(query);
    }

//    @GetMapping(value = "/getCollections", params = "apiKey")
//    public Set<MyCollection> getCollectionsByApiKey(@RequestParam(required = false) String apiKey) {
//        return this.userService.getCollectionsByApiKey(apiKey);
//    }

    //    update ------------------------------------------
    //    delete ------------------------------------------
    //    others ------------------------------------------


}
