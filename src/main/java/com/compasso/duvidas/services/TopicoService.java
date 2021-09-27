package com.compasso.duvidas.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.compasso.duvidas.dto.TopicoDTO;
import com.compasso.duvidas.dto.TopicoFormDTO;

public interface TopicoService {

	TopicoDTO save(TopicoFormDTO form);

	Page<TopicoDTO> findAll(Pageable page, String curso);
	
}
