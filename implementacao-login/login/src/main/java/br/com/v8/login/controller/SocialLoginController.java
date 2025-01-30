package br.com.v8.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class SocialLoginController {

    @GetMapping("/home")
    public String goHome(){
        return "index";
    }

    @GetMapping("/redirect")
    public String goLogin(){
        return "redirect";
    }
}
