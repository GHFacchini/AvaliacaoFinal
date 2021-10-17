package com.compasso.duvidas.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compasso.duvidas.dto.RespostaDTO;
import com.compasso.duvidas.dto.RespostaFormDTO;
import com.compasso.duvidas.services.RespostaService;

@RestController
@RequestMapping("respostas")
public class RespostaController {
    @Autowired
    private RespostaService respostaService;

    @GetMapping
    public ResponseEntity<Page<RespostaDTO>> findAll(@PageableDefault Pageable page) {
        return ResponseEntity.ok(respostaService.findAll(page));
    }
}
