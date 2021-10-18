package com.compasso.duvidas.services;

import com.compasso.duvidas.constants.Categoria;
import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.entities.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.compasso.duvidas.dto.CursoDTO;
import com.compasso.duvidas.dto.CursoFormDTO;

public interface CursoService {

	ResponseEntity<CursoDTO> save(CursoFormDTO form);

	Page<CursoDTO> findAll(Pageable page, Categoria categoria);


	ResponseEntity<?> findById(Long id);


	ResponseEntity<?> update(Long id, CursoFormDTO form);

	ResponseEntity<?> delete(Long id);
	
}
