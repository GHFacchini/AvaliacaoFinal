package com.compasso.duvidas.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.compasso.duvidas.dto.CursoDTO;
import com.compasso.duvidas.dto.TopicoDTO;
import com.compasso.duvidas.dto.TopicoFormDTO;

public interface TopicoService {

	ResponseEntity<TopicoDTO> save(TopicoFormDTO form);

	ResponseEntity<Page<TopicoDTO>> findAll(Pageable page, String titulo, String Curso);

	ResponseEntity<?> close(Long id);

	ResponseEntity<TopicoDTO> findById(Long id);

	ResponseEntity<TopicoDTO> update(Long id, TopicoFormDTO form);

	ResponseEntity<TopicoDTO> delete(Long id);
}
