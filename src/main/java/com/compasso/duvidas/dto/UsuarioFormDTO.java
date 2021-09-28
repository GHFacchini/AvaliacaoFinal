package com.compasso.duvidas.dto;

import com.compasso.duvidas.constants.TipoUsuario;
import com.compasso.duvidas.entities.Turma;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioFormDTO {
    @NotEmpty(message = "nome is required")
    private String nome;

    private Turma turma;

    @NotEmpty(message = "email is required")
    private	String email;

    @NotEmpty(message = "senha is required")
    private String senha;
    //se o valor não for passado deve ser cadastrado como bolsista
    private TipoUsuario tipoUsuario;
}
