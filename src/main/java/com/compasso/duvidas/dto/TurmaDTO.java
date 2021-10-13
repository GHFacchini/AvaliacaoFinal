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

    private List<Sprint> sprints = new ArrayList<>();

    public TurmaDTO(Turma turma) {
        this.id = turma.getId();
        this.nome = turma.getNome();
        List<Long> usuarios = new ArrayList<>();
        for(Usuario usuario : turma.getUsuarios()){
            System.out.println(usuario.getNome());
        }
        this.usuariosIds = usuarios;
    }
}
