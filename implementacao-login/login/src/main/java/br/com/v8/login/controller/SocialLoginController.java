package br.com.v8.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class SocialLoginController {

    @GetMapping
    public String goHome(){
        return "index";
    }

    @GetMapping("/redirect")
    public String goLogin(){
        return "redirect";
    }
}
