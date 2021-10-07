package com.compasso.duvidas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.compasso.duvidas.components.Disco;

@RestController
@RequestMapping("/arquivo")
public class ArquivoController {
	
	@Autowired
	private Disco disco;
	
	@PostMapping
	public ResponseEntity<?> upload(@RequestParam MultipartFile arquivo) {
		return disco.saveFile(arquivo);
	}
	
}
