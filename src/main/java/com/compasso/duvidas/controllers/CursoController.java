package com.compasso.duvidas.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.duvidas.dto.CursoDTO;
import com.compasso.duvidas.dto.CursoFormDTO;
import com.compasso.duvidas.services.CursoService;

@RestController
@RequestMapping("cursos")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@PostMapping
	public ResponseEntity<CursoDTO> save(@RequestBody @Valid CursoFormDTO form) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(cursoService.save(form));
	}
	
	@GetMapping
	public ResponseEntity <Page<CursoDTO>> findAll(@PageableDefault Pageable page) {
		return ResponseEntity.ok(cursoService.findAll(page));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CursoDTO> findById(@PathVariable Long id) {
		return cursoService.findById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CursoDTO> update(@PathVariable Long id, @RequestBody CursoFormDTO form) {
		return cursoService.update(id, form);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CursoDTO> delete(@PathVariable Long id) {
		return cursoService.delete(id);
	}
}
