package br.com.v8.login.controller;

import br.com.v8.login.model.DTO.ResetPasswordRequest;
import br.com.v8.login.model.Usuario;
import br.com.v8.login.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("🛠️ E-mail recebido no Controller: " + email);

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("E-mail não pode ser vazio!");
        }

        passwordResetService.sendResetLink(email);
        return ResponseEntity.ok("E-mail enviado com sucesso.");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }
}
