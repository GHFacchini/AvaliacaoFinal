package com.compasso.duvidas.services;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.compasso.duvidas.dto.RespostaDTO;
import com.compasso.duvidas.dto.RespostaFormDTO;

public interface RespostaService {

	ResponseEntity<RespostaDTO> save(@Valid RespostaFormDTO form);

	Page<RespostaDTO> findAll(Pageable page);

	ResponseEntity<RespostaDTO> findById(Long id);

	ResponseEntity<?> update(Long id, RespostaFormDTO form);

	ResponseEntity<RespostaDTO> delete(Long id);

}
