package com.compasso.duvidas.services;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.compasso.duvidas.dto.PerfilDTO;
import com.compasso.duvidas.dto.PerfilFormDTO;

public interface PerfilService {

	ResponseEntity<?> save(@Valid PerfilFormDTO form);

	Page <PerfilDTO> findAll(Pageable page);

	ResponseEntity<?> update(Long id, PerfilFormDTO form);

	ResponseEntity<?> delete(Long id);

}
