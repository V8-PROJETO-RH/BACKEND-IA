package br.com.v8.login.model.DTO;

import br.com.v8.login.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioCreatedDTO {
    Long id;
    String nome;
    String email;
    String dataNasc;
    String cpf;
    String tipoUsuario;

    public UsuarioCreatedDTO(Usuario usuario) {
        this.id = usuario.getIdUsuario();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.dataNasc = usuario.getDataNasc();
        this.cpf = usuario.getCpf();
        this.tipoUsuario = usuario.getTipoUsuario();
    }
}
