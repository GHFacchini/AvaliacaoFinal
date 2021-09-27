package com.compasso.duvidas.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.duvidas.dto.TopicoDTO;
import com.compasso.duvidas.dto.TopicoFormDTO;
import com.compasso.duvidas.services.TopicoService;

@RestController
@RequestMapping("topicos")
public class TopicoController {
	
	@Autowired
	private TopicoService topicoService;
	
	@PostMapping
	public ResponseEntity<TopicoDTO> save(@RequestBody @Valid TopicoFormDTO form) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(topicoService.save(form));
	}
	
	@GetMapping
	public ResponseEntity <Page<TopicoDTO>> findAll(@PageableDefault Pageable page,
			@RequestParam(required = false)String curso) {
		return ResponseEntity.ok(topicoService.findAll(page, curso));
	}
	
}
