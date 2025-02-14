package tech.v8.crudbackendmvp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.v8.crudbackendmvp.controller.usuario.FuncionarioController;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontCriacao;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontEdicao;
import tech.v8.crudbackendmvp.model.dto.usuario.funcionario.FuncionarioFrontResposta;
import tech.v8.crudbackendmvp.service.usuario.FuncionarioService;

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
        List<FuncionarioFrontResposta> mockedList = List.of(new FuncionarioFrontResposta());
        when(funcionarioService.list()).thenReturn(mockedList);

        List<FuncionarioFrontResposta> result = funcionarioController.list();

        verify(funcionarioService).list(); // Verifica se o serviço foi chamado
        assertNotNull(result);
        assertEquals(mockedList, result);
    }


    @Test
    void testCreate() {
        FuncionarioFrontCriacao newFuncionario = new FuncionarioFrontCriacao();
        FuncionarioFrontResposta createdFuncionario = new FuncionarioFrontResposta();
        when(funcionarioService.create(any(FuncionarioFrontCriacao.class))).thenReturn(createdFuncionario);

        FuncionarioFrontResposta result = funcionarioController.create(newFuncionario);

        verify(funcionarioService).create(newFuncionario);
        assertNotNull(result);
        assertEquals(createdFuncionario, result);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        FuncionarioFrontResposta mockedFuncionario = new FuncionarioFrontResposta();
        when(funcionarioService.findDTOById(id)).thenReturn(mockedFuncionario);

        FuncionarioFrontResposta result = funcionarioController.findById(id);

        verify(funcionarioService).findDTOById(id);
        assertNotNull(result);
        assertEquals(mockedFuncionario, result);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        FuncionarioFrontEdicao edicaoDTO = new FuncionarioFrontEdicao();
        FuncionarioFrontResposta updatedFuncionario = new FuncionarioFrontResposta();
        when(funcionarioService.update(eq(id), any(FuncionarioFrontEdicao.class))).thenReturn(updatedFuncionario);

        FuncionarioFrontResposta result = funcionarioController.update(id, edicaoDTO);

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
