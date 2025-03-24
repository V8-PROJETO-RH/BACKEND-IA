package tech.v8.dashboard.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tech.v8.dashboard.client.VagaClient;
import tech.v8.dashboard.exception.BadRequestException;
import tech.v8.dashboard.exception.ResourceNotFoundException;
import tech.v8.dashboard.model.VagaCandidatoAprovado;
import tech.v8.dashboard.model.dto.VagasAbertasDTO;
import tech.v8.dashboard.model.dto.VagasCandidatosAprovadosDTO;
import tech.v8.dashboard.model.dto.VagasFechadasDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class VagaService {

    private final VagaClient vagaClient;
    private static final Logger logger = LoggerFactory.getLogger(VagaService.class);

    @Cacheable("vagasAbertas")
    public VagasAbertasDTO obterVagasAbertas(){
        logger.info("Obtendo resumo de vagas abertas (verificando cache ou buscando do CRUD)...");

        var response = vagaClient.obterVagasPorStatus("aberta");
        logger.info("Resumo de vagas abertas obtido do CRUD");

        if (response == null || response.getVagas() == null) {
            logger.error("Resposta nula ou sem vagas do serviço CRUD");
            throw new ResourceNotFoundException("Não foram encontradas vagas abertas");
        }

        Integer qtdVagasAbertas = response.getVagas().size();
        logger.info("Total de vagas abertas encontradas: {}", qtdVagasAbertas);

        var vagas = response.getVagas().stream().map(vaga -> new VagasAbertasDTO.VagaDTO(
                vaga.getId(),
                vaga.getNome(),
                vaga.getTipo(),
                vaga.getLocalidade(),
                vaga.getModelo(),
                vaga.getCriacao(),
                vaga.getQuantidade(),
                vaga.getResponsavel() != null ? vaga.getResponsavel().nome() : "Não informado"
        )).toList();

        return new VagasAbertasDTO(
                qtdVagasAbertas,
                vagas);
    }

    @Cacheable("vagasFechadas")
    public VagasFechadasDTO obterVagasFechadas(){
        logger.info("Obtendo resumo de vagas fechadas (verificando cache ou buscando do CRUD)...");

        var response = vagaClient.obterVagasPorStatus("fechada");
        logger.info("Resumo de vagas fechadas obtido do CRUD");

        if (response == null || response.getVagas() == null) {
            logger.error("Resposta nula ou sem vagas do serviço CRUD");
            throw new ResourceNotFoundException("Não foram encontradas vagas fechadas");
        }

        Integer qtdVagasFechadas = response.getVagas().size();
        logger.info("Total de vagas fechadas encontradas: {}", qtdVagasFechadas);

        var vagas = response.getVagas().stream().map(vaga -> new VagasFechadasDTO.VagaDTO(
                vaga.getId(),
                vaga.getNome(),
                vaga.getTipo(),
                vaga.getLocalidade(),
                vaga.getModelo(),
                vaga.getCriacao(),
                vaga.getResponsavel() != null ? vaga.getResponsavel().nome() : "Não informado"
        )).toList();

        return new VagasFechadasDTO(
                qtdVagasFechadas,
                vagas);
    }

    @Cacheable("vagasCandidatoAprovado")
    public VagasCandidatosAprovadosDTO obterVagasCandidatosAprovados() {
        logger.info("Obtendo resumo de vagas com candidato aprovado");
        var response = vagaClient.obterVagasCandidatosAprovados();
        logger.info("Resumo de vagas com candidatos aprovados obtido do CRUD");

        if (response == null || response.getVagasAplicadas() == null) {
            logger.error("Resposta nula ou sem vagas aplicadas do serviço CRUD");
            throw new ResourceNotFoundException("Não foram encontradas vagas com candidatos aprovados");
        }

        logger.debug("Vagas aplicadas obtidas: {}", response.getVagasAplicadas());

        var vagasAplicadasCandidatoAprovado = response.getVagasAplicadas();
        if (vagasAplicadasCandidatoAprovado.isEmpty()) {
            logger.info("Nenhuma vaga com candidato aprovado encontrada");
            return new VagasCandidatosAprovadosDTO(0, new ArrayList<>());
        }

        try {
            // Agrupando vagas por ID para eliminar duplicatas
            var candidaturasAprovadas = vagasAplicadasCandidatoAprovado.stream()
                    .map(VagaCandidatoAprovado::getVaga)
                    .collect(Collectors.groupingBy(VagaCandidatoAprovado.Vaga::id));

            // Obtendo lista de vagas distintas
            List<VagaCandidatoAprovado.Vaga> vagasDistintas = candidaturasAprovadas.values().stream()
                    .map(List::getFirst)
                    .toList();

            int qtdVagasComCandidatosAprovados = vagasDistintas.size();
            
            // Criando a lista de vagas para o DTO
            List<VagasCandidatosAprovadosDTO.VagaDTO> vagasDTO = new ArrayList<>();

            // Para cada vaga distinta, encontrar seus candidatos aprovados
            vagasDistintas.forEach(vaga -> {
                if (vaga == null || vaga.id() == null) {
                    logger.warn("Encontrada vaga nula ou com ID nulo no processamento");
                    return;
                }
                
                // Filtrando candidatos aprovados para esta vaga específica
                var candidatosAprovados = vagasAplicadasCandidatoAprovado.stream()
                        .filter(vagaAplicada -> 
                            vagaAplicada.getVaga() != null && 
                            vagaAplicada.getVaga().id() != null && 
                            vagaAplicada.getVaga().id().equals(vaga.id()))
                        .toList();

                // Mapeando para o formato de CandidatoAprovado do DTO
                var candidatosDTO = candidatosAprovados.stream()
                        .map(vagaAplicada -> {
                            if (vagaAplicada.getCandidato() == null) {
                                logger.warn("Candidato nulo encontrado para a vaga {}", vaga.id());
                                return null;
                            }
                            
                            return new VagasCandidatosAprovadosDTO.CandidatoAprovado(
                                vagaAplicada.getCandidato().id(),
                                vagaAplicada.getCandidato().nome(),
                                vagaAplicada.getResultadoFinal() != null && vagaAplicada.getResultadoFinal().notaProva() != null ?
                                    vagaAplicada.getResultadoFinal().notaProva().doubleValue() : null,
                                vagaAplicada.getResultadoFinal() != null && vagaAplicada.getResultadoFinal().aderencia() != null ?
                                    vagaAplicada.getResultadoFinal().aderencia().doubleValue() : null
                            );
                        })
                        .filter(candidatoDTO -> candidatoDTO != null)
                        .toList();

                // Criando o objeto Vaga com seus candidatos aprovados
                var vagaDTO = new VagasCandidatosAprovadosDTO.VagaDTO(
                        vaga.id(),
                        vaga.nome() != null ? vaga.nome() : "Vaga sem nome",
                        candidatosDTO
                );
                
                vagasDTO.add(vagaDTO);
            });

            logger.info("Total de vagas com candidatos aprovados: {}", qtdVagasComCandidatosAprovados);
            
            // Criando e retornando o DTO final
            return new VagasCandidatosAprovadosDTO(
                    qtdVagasComCandidatosAprovados,
                    vagasDTO
            );
        } catch (Exception e) {
            logger.error("Erro ao processar dados de vagas com candidatos aprovados: {}", e.getMessage(), e);
            throw new BadRequestException("Erro ao processar dados de vagas com candidatos aprovados: " + e.getMessage(), e);
        }
    }
}
