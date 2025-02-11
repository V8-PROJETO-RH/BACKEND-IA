package tech.v8.crudbackendmvp.infra.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Documentação da API de CRUD")
                        .description("Esta é a documentação detalhada da API para o backend do projeto MVP." +
                                "\nInclui informações dos endpoints e exemplos.")
                        .version("1.0.0")
                        .contact(new Contact()
                                        .name("FilipeGuerreiro")
                                        .email("filipe.guerreiro@v8.tech")
//                                .url("https://www.empresa.com")
                        ));
//                        .license(new License()
//                                .name("Licença MIT")
//                                .url("https://opensource.org/licenses/MIT")));
    }
}