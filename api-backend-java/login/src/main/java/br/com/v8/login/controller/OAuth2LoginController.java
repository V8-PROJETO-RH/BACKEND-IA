package br.com.v8.login.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OAuth2LoginController {

    @GetMapping("/user-info")
    public Map<String, String> getUserInfo(@AuthenticationPrincipal OAuth2User oauthUser) {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("name", oauthUser.getAttribute("name"));
        userInfo.put("email", oauthUser.getAttribute("email"));
        return userInfo;
    }

}