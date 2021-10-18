package com.compasso.duvidas.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.compasso.duvidas.dto.CursoDTO;
import com.compasso.duvidas.dto.TopicoDTO;
import com.compasso.duvidas.dto.TopicoFormDTO;

public interface TopicoService {

	//no recurso /cursos
	ResponseEntity<?> save(Long id, TopicoFormDTO form);
	ResponseEntity<?> findAllFromCurso(Long id, Pageable page, String titulo, String Curso);
	ResponseEntity<?> findById(Long cursoId, Long topicoId);
	ResponseEntity<?> close(Long cursoId, Long topicoId);
	ResponseEntity<?> delete(Long cursoId, Long topicoId);
	ResponseEntity<?> update(Long cursoId, Long topicoId, TopicoFormDTO form);

	//fora do recurso /cursos
	ResponseEntity<Page<TopicoDTO>> findAll(Pageable page, String titulo, String Curso);

	ResponseEntity<?> findByCursoId(Pageable page, Long id);
}
