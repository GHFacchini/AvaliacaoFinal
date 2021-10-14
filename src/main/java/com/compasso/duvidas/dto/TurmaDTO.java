package com.compasso.duvidas.dto;

import com.compasso.duvidas.entities.Sprint;
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

    private List<Long> usuariosIds;

    private List<String> sprints = new ArrayList<>();

    public TurmaDTO(Turma turma) {
        this.id = turma.getId();
        this.nome = turma.getNome();
        usuariosInfo(turma);
        sprintsInfo(turma);
    }

    private void sprintsInfo(Turma turma) {
        List<String> sprints = new ArrayList<>();
        for(Sprint sprint : turma.getSprints()){
            sprints.add("Id: " + sprint.getId() + " Título: " + sprint.getTitulo());
        }
        this.sprints = sprints;
    }

    private void usuariosInfo(Turma turma) {
        List<Long> usuarios = new ArrayList<>();
        for(Usuario usuario : turma.getUsuarios()){
            usuarios.add(usuario.getId());
        }
        this.usuariosIds = usuarios;
    }
}
