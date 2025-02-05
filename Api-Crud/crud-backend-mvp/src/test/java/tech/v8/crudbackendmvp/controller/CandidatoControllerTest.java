package tech.v8.crudbackendmvp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.v8.crudbackendmvp.controller.usuario.CandidatoController;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontEdicao;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoFrontResposta;
import tech.v8.crudbackendmvp.model.dto.usuario.candidato.CandidatoPage;
import tech.v8.crudbackendmvp.service.usuario.CandidatoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CandidatoControllerTest {

    @InjectMocks
    private CandidatoController candidatoController;

    @Mock
    private CandidatoService candidatoService;

    @Test
    void testList() {
        // Mock para o méto do list()
        int page = 0;
        int size = 10;
        List<CandidatoFrontResposta> candidatos = List.of(new CandidatoFrontResposta());
        int totalPages = 1;
        long totalElements = 1L;
        CandidatoPage mockedPage = new CandidatoPage(candidatos, totalPages, totalElements);
        when(candidatoService.list(page, size)).thenReturn(mockedPage);

        CandidatoPage result = candidatoController.list(page, size);

        verify(candidatoService).list(page, size); // Verifica se o serviço foi chamado
        assertNotNull(result);
        assertEquals(mockedPage, result);
    }


    @Test
    void testCreate() {
        CandidatoFrontCriacao newCandidato = new CandidatoFrontCriacao();
        CandidatoFrontResposta createdCandidato = new CandidatoFrontResposta();
        when(candidatoService.create(any(CandidatoFrontCriacao.class))).thenReturn(createdCandidato);

        CandidatoFrontResposta result = candidatoController.create(newCandidato);

        verify(candidatoService).create(newCandidato);
        assertNotNull(result);
        assertEquals(createdCandidato, result);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        CandidatoFrontResposta mockedCandidato = new CandidatoFrontResposta();
        when(candidatoService.findDTOById(id)).thenReturn(mockedCandidato);

        CandidatoFrontResposta result = candidatoController.findById(id);

        verify(candidatoService).findDTOById(id);
        assertNotNull(result);
        assertEquals(mockedCandidato, result);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        CandidatoFrontEdicao edicaoDTO = new CandidatoFrontEdicao();
        CandidatoFrontResposta updatedCandidato = new CandidatoFrontResposta();
        when(candidatoService.update(eq(id), any(CandidatoFrontEdicao.class))).thenReturn(updatedCandidato);

        CandidatoFrontResposta result = candidatoController.update(id, edicaoDTO);

        verify(candidatoService).update(id, edicaoDTO);
        assertNotNull(result);
        assertEquals(updatedCandidato, result);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        candidatoController.delete(id);

        verify(candidatoService).delete(id);
    }
}
