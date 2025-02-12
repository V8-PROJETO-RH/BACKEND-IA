package br.com.v8.login.model.DTO;

import br.com.v8.login.model.Usuario;

public class UsuarioRespostaDTO {
    private Usuario usuario;
    private String mensagem;

    public UsuarioRespostaDTO(String mensagem, Usuario usuario) {
        this.usuario = usuario;
        this.mensagem = mensagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getMensagem() {
        return mensagem;
    }
}