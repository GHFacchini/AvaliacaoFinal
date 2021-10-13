package com.compasso.duvidas.dto;

import com.compasso.duvidas.constants.TipoUsuario;
import com.compasso.duvidas.entities.Turma;
import com.compasso.duvidas.entities.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;

    private String nome;

    private List<Long> turmasIds;

    private	String email;

    private TipoUsuario tipoUsuario;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        List<Long> turmas = new ArrayList<>();
        for(Turma turma : usuario.getTurmas()){
            turmas.add(usuario.getId());
        }
        this.turmasIds = turmas;
        this.email = usuario.getEmail();
        this.tipoUsuario = usuario.getTipoUsuario();
    }


}
