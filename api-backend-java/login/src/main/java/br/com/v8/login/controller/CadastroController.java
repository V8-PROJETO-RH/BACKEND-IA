package br.com.v8.login.controller;

import br.com.v8.login.model.DTO.LoginDTO;
import br.com.v8.login.model.DTO.UsuarioRegistroDTO;
import br.com.v8.login.model.Usuario;
import br.com.v8.login.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CadastroController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> registrarUsuario(@Validated @RequestBody UsuarioRegistroDTO usuarioDTO){
        Usuario usuario = usuarioService.registro(usuarioDTO);
            return ResponseEntity.ok(Map.of(
                "mensagem", "Usuário cadastrado com sucesso",
                "usuario", usuario
        ).toString());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Validated @RequestBody LoginDTO loginDTO) {
        try {
            String token = usuarioService.loginUsuario(loginDTO.getEmail(), loginDTO.getSenha());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
