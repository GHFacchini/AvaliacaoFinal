package com.compasso.duvidas.dto;

import com.compasso.duvidas.entities.Turma;
import com.compasso.duvidas.entities.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class TurmaDTO {

    private Long id;

    private String nome;

    private List<Usuario> usuarios = new ArrayList<>();

    private List<Usuario> moderadores = new ArrayList<>();

    public TurmaDTO(Turma turma) {
        this.id = turma.getId();
        this.nome = turma.getNome();
        this.usuarios = turma.getUsuarios();
        this.moderadores = turma.getModeradores();
    }
}
