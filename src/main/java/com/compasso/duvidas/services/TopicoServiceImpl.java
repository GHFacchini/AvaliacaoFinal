package com.compasso.duvidas.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.compasso.duvidas.dto.TopicoDTO;
import com.compasso.duvidas.dto.TopicoFormDTO;
import com.compasso.duvidas.entities.Topico;
import com.compasso.duvidas.repositories.TopicoRepository;

@Service
public class TopicoServiceImpl implements TopicoService{
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public TopicoDTO save(TopicoFormDTO form) {
		Topico entity = topicoRepository.save(mapper.map(form,  Topico.class));
		return mapper.map(entity, TopicoDTO.class);
	}

	@Override
	public Page<TopicoDTO> findAll(Pageable page, String curso) {
		Page<Topico> topicos;
		if(curso != null) topicos = topicoRepository.findByCurso(page, curso);
		else topicos = topicoRepository.findAll(page);
		Page<TopicoDTO> topicosDTOS = topicos.map(TopicoDTO::new);
		return topicosDTOS;
	}

	@Override
	public ResponseEntity<?> close(Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			Topico topicoFechado = topico.get();
			topicoFechado.close();
			topicoRepository.save(topicoFechado);
			return ResponseEntity.ok().body("Tópico '" + topicoFechado.getTitulo() 
			+ "' (ID: " + topicoFechado.getId() + ") foi FECHADO!");
		} else
			return ResponseEntity.notFound().build();
	}
	
}
