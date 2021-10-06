package com.compasso.duvidas.dto;

import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.entities.Sprint;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
public class SprintDTO {

    private Long id;

    private String titulo;

    @OneToMany
    private List<Curso> cursos;

    public SprintDTO(Sprint sprint) {
        this.id = sprint.getId();
        this.titulo = sprint.getTitulo();
        this.cursos = sprint.getCursos();
    }
}
