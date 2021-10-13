package com.compasso.duvidas.dto;

import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.entities.Sprint;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SprintDTO {

    private Long id;

    private String titulo;

    private List<String> cursos;

    public SprintDTO(Sprint sprint) {
        this.id = sprint.getId();
        this.titulo = sprint.getTitulo();
        cursosInfo(sprint);
    }

    private void cursosInfo(Sprint sprint){
        List<String> cursos = new ArrayList<>();
        for(Curso curso : sprint.getCursos()){
            cursos.add("Id: " + curso.getId() + " Nome: " + curso.getNome());
        }
        this.cursos = cursos;
    }
}
