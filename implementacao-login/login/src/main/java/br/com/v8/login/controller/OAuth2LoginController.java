package br.com.v8.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2LoginController {

    @GetMapping("/login/oauth2/code/linkedin")
    public String redirectLogin() {
        return "redirect:/login?code=linkedin";
    }

}