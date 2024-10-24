package com.compasso.duvidas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.compasso.duvidas.components.Disco;
import com.compasso.duvidas.services.RespostaService;

@RestController
@RequestMapping("/arquivo")
public class ArquivoController {
	
	@Autowired
	private Disco disco;
	
	@PostMapping("resposta/{id}")
	public ResponseEntity<?> upload(@PathVariable Long id, @RequestParam MultipartFile arquivo) {
		return disco.saveFile(id, arquivo);
	}
	
	@GetMapping("/{nome}")
	public ResponseEntity<?> downloadFile(@PathVariable String nome) {
		return disco.downloadFile(nome);
	}
	
}
