package com.compasso.duvidas.services;

import java.util.Optional;

import javax.transaction.Transactional;

import com.compasso.duvidas.dto.TurmaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<CursoDTO> save(CursoFormDTO form) {
		Curso entity = cursoRepository.save(mapper.map(form, Curso.class));
		CursoDTO cursoDTO = new CursoDTO(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoDTO);
	}

	@Override
	public Page<CursoDTO> findAll(Pageable page) {
		Page<Curso> cursos = cursoRepository.findAll(page);
		Page<CursoDTO> cursosDTOS = cursos.map(CursoDTO::new);
		return cursosDTOS;
	}

	@Override
	public ResponseEntity<CursoDTO> findById(Long id) {
		Optional<Curso> cursoOptional = cursoRepository.findById(id);
		if (cursoOptional.isPresent()) {
			return ResponseEntity.ok().body(new CursoDTO(cursoOptional.get()));
		}
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
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<CursoDTO> delete(Long id) {
		Optional<Curso> curso = cursoRepository.findById(id);
		if (curso.isPresent()) {
			cursoRepository.delete(curso.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
