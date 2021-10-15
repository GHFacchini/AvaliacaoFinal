package com.compasso.duvidas.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.duvidas.dto.PerfilDTO;
import com.compasso.duvidas.dto.PerfilFormDTO;
import com.compasso.duvidas.services.PerfilService;

@RestController
@RequestMapping("perfis")
public class PerfilController {
	
	@Autowired
	PerfilService perfilService;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid PerfilFormDTO form) {
		return perfilService.save(form);
	}
	
	@GetMapping
	public ResponseEntity <Page<PerfilDTO>> findAll(@PageableDefault Pageable page) {
		return ResponseEntity.ok(perfilService.findAll(page));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PerfilFormDTO form) {
		return perfilService.update(id, form);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return perfilService.delete(id);
	}
}
