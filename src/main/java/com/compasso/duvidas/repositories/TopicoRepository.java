package com.compasso.duvidas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.compasso.duvidas.entities.Topico;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
	Page<Topico> findByCurso(Pageable page, String curso);
	
	Page<Topico> findByTituloLike(Pageable page, String titulo);
	
	Page<Topico> findByCursoNome(Pageable page, String curso);

	Page<Topico> findByCurso_Id(Pageable page, Long id);



}
