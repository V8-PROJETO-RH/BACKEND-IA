package br.com.v8.login.service;

import br.com.v8.login.config.JwtUtil;
import br.com.v8.login.model.DTO.UsuarioRegistroDTO;
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
    public Usuario registro(UsuarioRegistroDTO usuarioDTO) {
        Usuario usuario = new Usuario();

        if (CadastroRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setNome(usuarioDTO.getNome());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setDataNasc(usuarioDTO.getDataNasc());

        usuario.setTipoUsuario("USUARIO_COMUM");

        return cadastroRepository.save(usuario);
    }

    public String loginUsuario(String email, String senha) {
        Optional<Usuario> optionalUsuario = cadastroRepository.findByEmail(email);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            if (passwordEncoder.matches(senha, usuario.getSenha())){
                return jwtUtil.generateToken(usuario.getEmail());
            }
        }
        throw new RuntimeException("Invalid credentials");
    }

}
