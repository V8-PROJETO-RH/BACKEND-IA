package tech.v8.crudbackendmvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CrudBackendMvpApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudBackendMvpApplication.class, args);
    }
}
