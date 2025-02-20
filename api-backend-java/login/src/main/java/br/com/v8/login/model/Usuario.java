package br.com.v8.login.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios_cadastrados")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull
    private String dataNasc;

    @NotBlank
    private String cpf;

    @NotBlank
    private String senha;

    @Transient
    private String confirmarSenha;

    @NotBlank
    private String tipoUsuario;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<UserRole> roles;

    public Usuario(Long idUsuario, String nome, String email, String senha, String tipoUsuario, List<UserRole> roles) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.roles = roles;
    }

    @PrePersist
    @PreUpdate
    private void formatEmail() {
        this.email = this.email.toLowerCase().trim();
    }

    public Usuario() {
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public @NotBlank String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(@NotBlank String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank String getSenha() {
        return senha;
    }

    public void setSenha(@NotBlank String senha) {
        this.senha = senha;
    }
}
