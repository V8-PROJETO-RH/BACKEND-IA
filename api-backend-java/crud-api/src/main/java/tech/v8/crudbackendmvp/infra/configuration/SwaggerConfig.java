package tech.v8.crudbackendmvp.infra.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8082}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("V8.TECH CRUD API")
                        .description("API de serviços CRUD para gerenciamento de vagas, candidatos e funcionários do sistema de recrutamento e seleção da V8.TECH. " +
                                "Esta API fornece endpoints para criar, ler, atualizar e excluir registros relacionados ao processo de recrutamento.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FilipeGuerreiro")
                                .email("filipe.guerreiro@v8.tech")
                                .url("https://www.v8.tech"))
                        // .license(new License()
                        //         .name("Proprietary")
                        //         .url("https://www.v8.tech/terms"))
                        )
                .servers(getServers())
                .tags(getTags());
    }

    private List<Server> getServers() {
        Server localServer = new Server()
                .url("http://localhost:" + serverPort)
                .description("Servidor de desenvolvimento local");
        Server cloudServer = new Server()
                .url("http://34.172.20.219:" + serverPort)
                .description("Servidor de desenvolvimento na nuvem");

        return Arrays.asList(localServer, cloudServer);
    }

    private List<Tag> getTags() {
        return Arrays.asList(

        );
    }
}