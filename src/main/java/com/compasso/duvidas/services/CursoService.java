package com.compasso.duvidas.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.compasso.duvidas.dto.CursoDTO;
import com.compasso.duvidas.dto.CursoFormDTO;

public interface CursoService {

	CursoDTO save(CursoFormDTO form);

	Page<CursoDTO> findAll(Pageable page);

	ResponseEntity<CursoDTO> findById(Long id);

	ResponseEntity<CursoDTO> update(Long id, CursoFormDTO form);

	ResponseEntity<CursoDTO> delete(Long id);
	
}
