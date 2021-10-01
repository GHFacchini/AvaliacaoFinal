package com.compasso.duvidas.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.compasso.duvidas.dto.CursoDTO;
import com.compasso.duvidas.dto.CursoFormDTO;
import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.repositories.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CursoRepository cursoRepository;

	@Override
	@Transactional
	public CursoDTO save(CursoFormDTO form) {
		Curso entity = cursoRepository.save(mapper.map(form, Curso.class));
		return mapper.map(entity, CursoDTO.class);
	}

	@Override
	public Page<CursoDTO> findAll(Pageable page) {
		Page<Curso> cursos = cursoRepository.findAll(page);
		Page<CursoDTO> cursosDTOS = cursos.map(CursoDTO::new);
		return cursosDTOS;
	}

	@Override
	public ResponseEntity<CursoDTO> findById(Long id) {
		Optional<Curso> curso = cursoRepository.findById(id);
		if (curso.isPresent()) {
			return ResponseEntity.ok().body(mapper.map(curso.get(), CursoDTO.class));
		} else
			return ResponseEntity.notFound().build();
	}

	@Override
	@Transactional
	public ResponseEntity<CursoDTO> update(Long id, CursoFormDTO form) {
		Optional<Curso> curso = cursoRepository.findById(id);
		if (curso.isPresent()) {
			Curso entity = curso.get();
			if (form.getNome() != null)
				entity.setNome(form.getNome());
			if (form.getCategoria() != null)
				entity.setCategoria(form.getCategoria());

			cursoRepository.save(entity);

			return ResponseEntity.ok().body(mapper.map(entity, CursoDTO.class));
		} else
			return ResponseEntity.notFound().build();
	}




	@Override
	public ResponseEntity<CursoDTO> delete(Long id) {
		Optional<Curso> curso = cursoRepository.findById(id);
		if (curso.isPresent()) {
			cursoRepository.delete(curso.get());
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}

}
