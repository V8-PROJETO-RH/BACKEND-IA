package br.com.v8.login.service;

import br.com.v8.login.infra.exception.ValidationException;
import br.com.v8.login.model.Email;
import br.com.v8.login.model.PasswordResetToken;
import br.com.v8.login.model.Usuario;
import br.com.v8.login.repository.CadastroRepository;
import br.com.v8.login.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final CadastroRepository userRepository;
    private final EmailService emailService;

    public void sendResetLink(String email) {
        Optional<Usuario> optionalUser = userRepository.findByEmail(email.trim());

        if (optionalUser.isEmpty()) {
            throw new ValidationException("Nenhum usuário encontrado com este e-mail.");
        }

        Usuario user = optionalUser.get();

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUsuario(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        if (resetToken.isExpired()){
            tokenRepository.delete(resetToken);
        }

        tokenRepository.save(resetToken);

        String resetUrl = "http://localhost:5173/reset-password?token=" + token;
        String message = """
                    Olá, %s!
                    Para redefinir sua senha, clique no link abaixo:
                    %s
                """.formatted(user.getNome(), resetUrl);
        String contentType = "text/html";
        Email emailSend = new Email(email, "Redefinição de senha", message, contentType);

        emailService.sendEmail(emailSend);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ValidationException("Token inválido"));

        if (resetToken.isExpired()) {
            throw new ValidationException("Token expirado");
        }

        Usuario user = resetToken.getUsuario();
        user.setSenha(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }
}
