package com.mvp.backend.backendmvp.controller;

import com.mvp.backend.backendmvp.model.dto.candidato.CandidatoFrontDTOCriacao;
import com.mvp.backend.backendmvp.model.dto.candidato.CandidatoFrontDTOEdicao;
import com.mvp.backend.backendmvp.model.dto.candidato.CandidatoFrontDTOResposta;
import com.mvp.backend.backendmvp.service.CandidatoService;
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
class CandidatoControllerTest {

    @InjectMocks
    private CandidatoController candidatoController;

    @Mock
    private CandidatoService candidatoService;

    @Test
    void testList() {
        // Mock para o métod o list()
        List<CandidatoFrontDTOResposta> mockedList = List.of(new CandidatoFrontDTOResposta());
        when(candidatoService.list()).thenReturn(mockedList);

        List<CandidatoFrontDTOResposta> result = candidatoController.list();

        verify(candidatoService).list(); // Verifica se o serviço foi chamado
        assertNotNull(result);
        assertEquals(mockedList, result);
    }


    @Test
    void testCreate() {
        CandidatoFrontDTOCriacao newCandidato = new CandidatoFrontDTOCriacao();
        CandidatoFrontDTOResposta createdCandidato = new CandidatoFrontDTOResposta();
        when(candidatoService.create(any(CandidatoFrontDTOCriacao.class))).thenReturn(createdCandidato);

        CandidatoFrontDTOResposta result = candidatoController.create(newCandidato);

        verify(candidatoService).create(newCandidato);
        assertNotNull(result);
        assertEquals(createdCandidato, result);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        CandidatoFrontDTOResposta mockedCandidato = new CandidatoFrontDTOResposta();
        when(candidatoService.findById(id)).thenReturn(mockedCandidato);

        CandidatoFrontDTOResposta result = candidatoController.findById(id);

        verify(candidatoService).findById(id);
        assertNotNull(result);
        assertEquals(mockedCandidato, result);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        CandidatoFrontDTOEdicao edicaoDTO = new CandidatoFrontDTOEdicao();
        CandidatoFrontDTOResposta updatedCandidato = new CandidatoFrontDTOResposta();
        when(candidatoService.update(eq(id), any(CandidatoFrontDTOEdicao.class))).thenReturn(updatedCandidato);

        CandidatoFrontDTOResposta result = candidatoController.update(id, edicaoDTO);

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
