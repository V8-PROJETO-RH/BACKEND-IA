package br.com.v8.login.repository;

import br.com.v8.login.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CadastroRepository extends JpaRepository <Usuario, Long> {
    static boolean existsByEmail(String email) {
        return false;
    }

    Optional<Usuario> findByNome(String nome);
}
