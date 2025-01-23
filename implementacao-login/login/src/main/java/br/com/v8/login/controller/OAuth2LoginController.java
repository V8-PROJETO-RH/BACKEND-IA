package br.com.v8.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2LoginController {

    @GetMapping("/oauth-error")
    public String redirectError() {
        return "redirect:/login?error";
    }
}