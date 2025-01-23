package br.com.v8.login.controller;

import br.com.v8.login.service.LinkedinOidUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class SocialLoginController {

    @Autowired
    private LinkedinOidUserService linkedInApiService;

    @GetMapping("/home")
    public String goHome(){
        return "index";
    }

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal OAuth2AuthenticationToken authentication) {
        String accessToken = linkedInApiService.getAccessToken(authentication);
        return linkedInApiService.getUserInfoFromLinkedIn(accessToken).toString();
    }

    @GetMapping("/redirect")
    public String goLogin(){
        return "redirect";
    }
}
