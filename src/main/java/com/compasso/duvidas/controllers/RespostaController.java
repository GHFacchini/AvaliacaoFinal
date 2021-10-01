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

import com.compasso.duvidas.dto.RespostaDTO;
import com.compasso.duvidas.dto.RespostaFormDTO;
import com.compasso.duvidas.services.RespostaService;

@RestController
@RequestMapping("respostas")
public class RespostaController {
	@Autowired
	private RespostaService respostaService;
	
	@PostMapping
	public ResponseEntity<RespostaDTO> save(@RequestBody @Valid RespostaFormDTO form) {
		return respostaService.save(form);
	}
	
	@GetMapping
	public ResponseEntity <Page<RespostaDTO>> findAll(@PageableDefault Pageable page) {
		return ResponseEntity.ok(respostaService.findAll(page));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RespostaDTO> findById(@PathVariable Long id) {
		return respostaService.findById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RespostaFormDTO form) {
		return respostaService.update(id, form);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<RespostaDTO> delete(@PathVariable Long id) {
		return respostaService.delete(id);
	}
}
