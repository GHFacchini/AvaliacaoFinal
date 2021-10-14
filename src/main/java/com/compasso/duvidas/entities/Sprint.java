package com.compasso.duvidas.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sprints")
@Data
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToMany
    private List<Curso> cursos = new ArrayList<>();

    @Override
    public String toString() {
        List<Long> cursosIds = new ArrayList<>();
        for(Curso curso : this.cursos){
            cursosIds.add(curso.getId());
        }
        return "Sprint{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", cursos=" + cursosIds +
                '}';
    }
}
