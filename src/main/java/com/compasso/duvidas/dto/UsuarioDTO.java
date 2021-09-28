package com.compasso.duvidas.dto;

import com.compasso.duvidas.constants.TipoUsuario;
import com.compasso.duvidas.entities.Turma;
import com.compasso.duvidas.entities.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;

    private String nome;

    private Turma turma;

    private	String email;

    private TipoUsuario tipoUsuario;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.turma = usuario.getTurma();
        this.email = usuario.getEmail();
        this.tipoUsuario = usuario.getTipoUsuario();
    }
}
