package br.com.v8.login.service;

import br.com.v8.login.config.JwtUtil;
import br.com.v8.login.model.Usuario;
import br.com.v8.login.repository.CadastroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private CadastroRepository cadastroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Optional<Usuario> findByUsername(String nome) {
        return cadastroRepository.findByNome(nome);
    }

    @Transactional
    public Usuario registro(Usuario usuario) {
        if (CadastroRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return cadastroRepository.save(usuario);
    }

    public String loginUsuario(Usuario usuario) {
        Optional<Usuario> optionalUser = findByUsername(usuario.getNome());
        if (optionalUser.isPresent() && passwordEncoder.matches(usuario.getSenha(), optionalUser.get().getSenha())) {
            return jwtUtil.generateToken(usuario.getNome());
        }
        throw new RuntimeException("Invalid credentials");
    }


}
