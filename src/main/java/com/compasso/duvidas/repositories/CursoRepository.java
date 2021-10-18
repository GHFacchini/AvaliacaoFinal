package com.compasso.duvidas.repositories;

import com.compasso.duvidas.constants.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.compasso.duvidas.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Page<Curso> findByCategoria(Pageable page, Categoria categoria);
}
