package br.com.v8.login.service;

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
        System.out.println("🔍 Procurando usuário com email: " + email);
        Optional<Usuario> optionalUser = userRepository.findByEmail(email.trim());

        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("Nenhum usuário encontrado com este e-mail.");
        }

        Usuario user = optionalUser.get();

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUsuario(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        tokenRepository.save(resetToken);

        String resetUrl = "http://localhost:5173/reset-password?token=" + token;
        String message = "<p>Olá, " + user.getNome() + "!</p>"
                + "<p>Para redefinir sua senha, clique no link abaixo:</p>"
                + "<a href=\"" + resetUrl + "\">Redefinir Senha</a>";
        Email emailSend = new Email(email, "Redefinição de senha", message);

        emailService.sendEmail(emailSend);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (resetToken.isExpired()) {
            throw new IllegalArgumentException("Token expirado");
        }

        Usuario user = resetToken.getUsuario();
        user.setSenha(new BCryptPasswordEncoder().encode(newPassword));

        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }
}
