package com.compasso.duvidas.dto;

import java.util.ArrayList;
import java.util.List;

import com.compasso.duvidas.entities.Turma;
import com.compasso.duvidas.entities.Usuario;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;

    private String nome;

    private List<Long> turmasIds;

    private	String email;

    private String perfilUsuario;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        List<Long> turmas = new ArrayList<>();
        for(Turma turma : usuario.getTurmas()){
            turmas.add(turma.getId());
        }
        this.turmasIds = turmas;
        this.email = usuario.getEmail();
        this.perfilUsuario = usuario.getPerfis().get(0).getAuthority();
    }


}
