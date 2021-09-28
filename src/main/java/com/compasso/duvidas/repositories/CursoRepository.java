package com.compasso.duvidas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.compasso.duvidas.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	
}
