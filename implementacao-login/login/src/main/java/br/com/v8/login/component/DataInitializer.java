package br.com.v8.login.component;

import br.com.v8.login.model.Role;
import br.com.v8.login.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    @Autowired
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            adminRole.setRole("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (!roleRepository.existsByName("ROLE_USER")) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            userRole.setRole("ROLE_USER");
            roleRepository.save(userRole);
        }
    }

}

