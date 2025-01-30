package br.com.v8.login.component;

import br.com.v8.login.config.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            String email = oidcUser.getAttribute("email");
            String token = jwtUtil.generateToken(email);

            response.addHeader("Set-Cookie", "token=" + token + "; HttpOnly; Path=/");
            response.sendRedirect("http://localhost:8081/index.html?token=" + token);
        }
    }
}