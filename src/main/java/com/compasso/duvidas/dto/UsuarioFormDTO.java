package com.compasso.duvidas.dto;

import javax.validation.constraints.NotEmpty;

import com.compasso.duvidas.constants.TipoUsuario;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioFormDTO {
    @NotEmpty(message = "nome is required")
    private String nome;

    private List<Long> turmasIds;

    @NotEmpty(message = "email is required")
    private	String email;

    @NotEmpty(message = "senha is required")
    private String senha;
    //se o valor não for passado deve ser cadastrado como bolsista
    private TipoUsuario tipoUsuario;
}
