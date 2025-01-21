package com.mvp.backend.backendmvp.controller;

import com.mvp.backend.backendmvp.model.dto.vaga.VagaFrontDTOCriacao;
import com.mvp.backend.backendmvp.model.dto.vaga.VagaFrontDTOEdicao;
import com.mvp.backend.backendmvp.model.dto.vaga.VagaFrontDTOResposta;
import com.mvp.backend.backendmvp.service.VagaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        // Mock para o método list()
        List<VagaFrontDTOResposta> mockedList = List.of(new VagaFrontDTOResposta());
        when(vagaService.list()).thenReturn(mockedList);

        List<VagaFrontDTOResposta> result = vagaController.list();

        verify(vagaService).list(); // Verifica se o serviço foi chamado
        assertNotNull(result);
        assertEquals(mockedList, result);
    }

    @Test
    void testCreate() {
        VagaFrontDTOCriacao newVaga = new VagaFrontDTOCriacao();
        VagaFrontDTOResposta createdVaga = new VagaFrontDTOResposta();
        when(vagaService.create(any(VagaFrontDTOCriacao.class))).thenReturn(createdVaga);

        VagaFrontDTOResposta result = vagaController.create(newVaga);

        verify(vagaService).create(newVaga);
        assertNotNull(result);
        assertEquals(createdVaga, result);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        VagaFrontDTOResposta mockedVaga = new VagaFrontDTOResposta();
        when(vagaService.findById(id)).thenReturn(mockedVaga);

        VagaFrontDTOResposta result = vagaController.findById(id);

        verify(vagaService).findById(id);
        assertNotNull(result);
        assertEquals(mockedVaga, result);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        VagaFrontDTOEdicao edicaoDTO = new VagaFrontDTOEdicao();
        VagaFrontDTOResposta updatedVaga = new VagaFrontDTOResposta();
        when(vagaService.update(eq(id), any(VagaFrontDTOEdicao.class))).thenReturn(updatedVaga);

        VagaFrontDTOResposta result = vagaController.update(id, edicaoDTO);

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
