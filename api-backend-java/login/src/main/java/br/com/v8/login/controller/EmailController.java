package br.com.v8.login.controller;

import br.com.v8.login.model.Email;
import br.com.v8.login.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mail")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody Email email) {
        emailService.sendEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("mensagem", "Email enviado com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
