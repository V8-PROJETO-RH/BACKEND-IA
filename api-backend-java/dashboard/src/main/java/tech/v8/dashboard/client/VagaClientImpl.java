package tech.v8.dashboard.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import tech.v8.dashboard.exception.ResourceNotFoundException;
import tech.v8.dashboard.exception.ServiceUnavailableException;
import tech.v8.dashboard.model.VagasResponse;
import tech.v8.dashboard.model.VagasCandidatosAprovadosResponse;

import java.net.ConnectException;

@Component
public class VagaClientImpl implements VagaClient {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(VagaClientImpl.class);

    @Value("${crud.service.base-url}")
    private String crudUrl;

    @Autowired
    public VagaClientImpl(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public VagasResponse obterVagasPorStatus(String status) {
        String url = crudUrl + "/vagas/search?status_like=%s&page=0&size=1000".formatted(status);
        logger.info("Fazendo requisição para: {}", url);

        try {
            ResponseEntity<VagasResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    VagasResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new ServiceUnavailableException("Resposta inesperada do serviço CRUD: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Recurso não encontrado: {}", e.getMessage());
            throw new ResourceNotFoundException("Não foi possível encontrar vagas com o status: " + status, e);
        } catch (HttpServerErrorException e) {
            logger.error("Erro no servidor CRUD: {}", e.getMessage());
            throw new ServiceUnavailableException("Erro no serviço CRUD: " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            logger.error("Erro de acesso ao serviço CRUD: {}", e.getMessage());
            
            if (e.getCause() instanceof ConnectException) {
                throw new ServiceUnavailableException("Serviço CRUD está indisponível. Verifique se o serviço está em execução.", e);
            }
            
            throw new ServiceUnavailableException("Erro ao acessar o serviço CRUD: " + e.getMessage(), e);
        }
    }

    @Override
    public VagasCandidatosAprovadosResponse obterVagasCandidatosAprovados() {
        String url = crudUrl + "/vagas-aplicadas/search?status_like=aprovado&page=0&size=1000";
        logger.info("Fazendo requisição para: {}", url);

        try {
            ResponseEntity<VagasCandidatosAprovadosResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    VagasCandidatosAprovadosResponse.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new ServiceUnavailableException("Resposta inesperada do serviço CRUD: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Recurso não encontrado: {}", e.getMessage());
            throw new ResourceNotFoundException("Não foi possível encontrar vagas com candidatos aprovados", e);
        } catch (HttpServerErrorException e) {
            logger.error("Erro no servidor CRUD: {}", e.getMessage());
            throw new ServiceUnavailableException("Erro no serviço CRUD: " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            logger.error("Erro de acesso ao serviço CRUD: {}", e.getMessage());
            
            if (e.getCause() instanceof ConnectException) {
                throw new ServiceUnavailableException("Serviço CRUD está indisponível. Verifique se o serviço está em execução.", e);
            }
            
            throw new ServiceUnavailableException("Erro ao acessar o serviço CRUD: " + e.getMessage(), e);
        }
    }
}
