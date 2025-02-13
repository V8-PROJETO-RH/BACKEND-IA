package tech.v8.crudbackendmvp.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permite todas as origens (ajuste para produção!)
        config.addAllowedOrigin("*");

        // Permite todos os métodos (GET, POST, etc.)
        config.addAllowedMethod("*");

        // Permite todos os headers
        config.addAllowedHeader("*");

        // Expõe headers específicos (opcional)
        config.addExposedHeader("Authorization");

        // Configura para todas as rotas
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
