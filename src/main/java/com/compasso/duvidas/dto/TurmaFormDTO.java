package com.compasso.duvidas.dto;

import com.compasso.duvidas.entities.Usuario;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class TurmaFormDTO {

    @NotEmpty
    private String nome;

    private List<Usuario> usuarios;

    private List<Usuario> moderadores;

}