package br.com.v8.login.service;

import br.com.v8.login.config.JwtUtil;
import br.com.v8.login.infra.exception.ValidationException;
import br.com.v8.login.model.DTO.UsuarioRegistroDTO;
import br.com.v8.login.model.Usuario;
import br.com.v8.login.repository.CadastroRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

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
        validarCampos(usuarioDTO);
        validarCPF(usuarioDTO.getCpf());
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setNome(usuarioDTO.getNome());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setDataNasc(usuarioDTO.getDataNasc());
        usuario.setTipoUsuario("USUARIO_COMUM");

        return cadastroRepository.save(usuario);
    }

    @Transactional
    public Usuario registroAdmin(UsuarioRegistroDTO usuarioDTO) {
        validarCampos(usuarioDTO);
        validarCPF(usuarioDTO.getCpf());
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setNome(usuarioDTO.getNome());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setDataNasc(usuarioDTO.getDataNasc());
        usuario.setTipoUsuario("USUARIO_ADMIN");

        return cadastroRepository.save(usuario);
    }

    private void validarCampos(UsuarioRegistroDTO usuarioDTO) {
        if (cadastroRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new ValidationException("Email já está em uso");
        }

        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isBlank() ||
                usuarioDTO.getSenha() == null || usuarioDTO.getSenha().isBlank() ||
                usuarioDTO.getConfirmarSenha() == null || usuarioDTO.getConfirmarSenha().isBlank() ||
                usuarioDTO.getNome() == null || usuarioDTO.getNome().isBlank() ||
                usuarioDTO.getCpf() == null || usuarioDTO.getCpf().isBlank() ||
                usuarioDTO.getDataNasc() == null) {
            throw new ValidationException( "Todos os campos são obrigatórios");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento;
        try {
            dataNascimento = LocalDate.parse(usuarioDTO.getDataNasc(), formatter);
        } catch (DateTimeParseException e) {
            throw new ValidationException("A data de nascimento deve estar no formato dd/MM/yyyy.");
        }

        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new ValidationException("A data de nascimento não pode estar no futuro.");
        }

        if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmarSenha())) {
            throw new ValidationException("As senhas não coincidem");
        }

        if (!usuarioDTO.getNome().contains(" ")) {
            throw new ValidationException("O nome deve conter pelo menos um sobrenome");
        }
    }

    private void validarCPF(String cpf) {
        String regex = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
        if (!Pattern.matches(regex, cpf)) {
            throw new ValidationException("CPF inválido. O formato correto é XXX.XXX.XXX-XX");
        }
    }


    public String loginUsuario(String email, String senha) {
        Optional<Usuario> optionalUsuario = cadastroRepository.findByEmail(email);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            if (passwordEncoder.matches(senha, usuario.getSenha())){
                return jwtUtil.generateToken(usuario.getEmail());
            }
        }
        throw new ValidationException("Credenciais inválidas");
    }

}
