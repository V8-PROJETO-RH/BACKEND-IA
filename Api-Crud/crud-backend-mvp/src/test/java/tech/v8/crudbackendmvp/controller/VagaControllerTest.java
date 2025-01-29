package tech.v8.crudbackendmvp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontEdicao;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaFrontResposta;
import tech.v8.crudbackendmvp.model.dto.vaga.VagaPage;
import tech.v8.crudbackendmvp.service.VagaService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VagaControllerTest {

    @InjectMocks
    private VagaController vagaController;

    @Mock
    private VagaService vagaService;

    @Test
    void testList() {
        // Mock para o méto do list()
        int page = 0;
        int size = 10;
        List<VagaFrontResposta> vagas = List.of(new VagaFrontResposta());
        int totalPages = 1;
        long totalElements = 1L;
        VagaPage mockedPage = new VagaPage(vagas, totalPages, totalElements);
        when(vagaService.list(page, size)).thenReturn(mockedPage);

        VagaPage result = vagaController.list(page, size);

        verify(vagaService).list(page, size); // Verifica se o serviço foi chamado
        assertNotNull(result);
        assertEquals(mockedPage, result);
    }

    @Test
    void testCreate() {
        VagaFrontCriacao newVaga = new VagaFrontCriacao();
        VagaFrontResposta createdVaga = new VagaFrontResposta();
        when(vagaService.create(any(VagaFrontCriacao.class))).thenReturn(createdVaga);

        VagaFrontResposta result = vagaController.create(newVaga);

        verify(vagaService).create(newVaga);
        assertNotNull(result);
        assertEquals(createdVaga, result);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        VagaFrontResposta mockedVaga = new VagaFrontResposta();
        when(vagaService.findById(id)).thenReturn(mockedVaga);

        VagaFrontResposta result = vagaController.findById(id);

        verify(vagaService).findById(id);
        assertNotNull(result);
        assertEquals(mockedVaga, result);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        VagaFrontEdicao edicaoDTO = new VagaFrontEdicao();
        VagaFrontResposta updatedVaga = new VagaFrontResposta();
        when(vagaService.update(eq(id), any(VagaFrontEdicao.class))).thenReturn(updatedVaga);

        VagaFrontResposta result = vagaController.update(id, edicaoDTO);

        verify(vagaService).update(id, edicaoDTO);
        assertNotNull(result);
        assertEquals(updatedVaga, result);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        vagaController.delete(id);

        verify(vagaService).delete(id);
    }
}
