package br.com.v8.login.controller;

import br.com.v8.login.infra.exception.ValidationException;
import br.com.v8.login.model.DTO.ResetPasswordRequest;
import br.com.v8.login.model.DTO.UsuarioCreatedDTO;
import br.com.v8.login.model.Usuario;
import br.com.v8.login.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    @Autowired
    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("🛠️ E-mail recebido no Controller: " + email);

        if (email == null || email.isEmpty()) {
            throw new ValidationException("E-mail não pode ser vazio!");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "E-mail enviado com sucesso.");
        passwordResetService.sendResetLink(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/reset")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Senha redefinida com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
