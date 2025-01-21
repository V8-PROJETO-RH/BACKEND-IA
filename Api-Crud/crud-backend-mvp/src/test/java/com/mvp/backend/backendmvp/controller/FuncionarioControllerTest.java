package com.mvp.backend.backendmvp.controller;

import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioFrontDTOCriacao;
import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioFrontDTOEdicao;
import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioFrontDTOResposta;
import com.mvp.backend.backendmvp.model.dto.funcionario.FuncionarioPageDTO;
import com.mvp.backend.backendmvp.service.FuncionarioService;
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
class FuncionarioControllerTest {

    @InjectMocks
    private FuncionarioController funcionarioController;

    @Mock
    private FuncionarioService funcionarioService;

    @Test
    void testList() {
        // Mock para o método list()
        List<FuncionarioFrontDTOResposta> mockedList = List.of(new FuncionarioFrontDTOResposta());
        when(funcionarioService.list()).thenReturn(mockedList);

        List<FuncionarioFrontDTOResposta> result = funcionarioController.list();

        verify(funcionarioService).list(); // Verifica se o serviço foi chamado
        assertNotNull(result);
        assertEquals(mockedList, result);
    }


    @Test
    void testCreate() {
        FuncionarioFrontDTOCriacao newFuncionario = new FuncionarioFrontDTOCriacao();
        FuncionarioFrontDTOResposta createdFuncionario = new FuncionarioFrontDTOResposta();
        when(funcionarioService.create(any(FuncionarioFrontDTOCriacao.class))).thenReturn(createdFuncionario);

        FuncionarioFrontDTOResposta result = funcionarioController.create(newFuncionario);

        verify(funcionarioService).create(newFuncionario);
        assertNotNull(result);
        assertEquals(createdFuncionario, result);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        FuncionarioFrontDTOResposta mockedFuncionario = new FuncionarioFrontDTOResposta();
        when(funcionarioService.findById(id)).thenReturn(mockedFuncionario);

        FuncionarioFrontDTOResposta result = funcionarioController.findById(id);

        verify(funcionarioService).findById(id);
        assertNotNull(result);
        assertEquals(mockedFuncionario, result);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        FuncionarioFrontDTOEdicao edicaoDTO = new FuncionarioFrontDTOEdicao();
        FuncionarioFrontDTOResposta updatedFuncionario = new FuncionarioFrontDTOResposta();
        when(funcionarioService.update(eq(id), any(FuncionarioFrontDTOEdicao.class))).thenReturn(updatedFuncionario);

        FuncionarioFrontDTOResposta result = funcionarioController.update(id, edicaoDTO);

        verify(funcionarioService).update(id, edicaoDTO);
        assertNotNull(result);
        assertEquals(updatedFuncionario, result);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        funcionarioController.delete(id);

        verify(funcionarioService).delete(id);
    }
}
