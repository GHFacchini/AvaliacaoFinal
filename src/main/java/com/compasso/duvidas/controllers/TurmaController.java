package com.compasso.duvidas.controllers;

import com.compasso.duvidas.dto.TurmaDTO;
import com.compasso.duvidas.dto.TurmaFormDTO;
import com.compasso.duvidas.services.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    public ResponseEntity<TurmaDTO> save(@RequestBody @Valid TurmaFormDTO form){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turmaService.save(form));
    }

    @GetMapping
    public ResponseEntity<Page<TurmaDTO>> findAll(@PageableDefault Pageable page){
        return ResponseEntity.ok(turmaService.findAll(page));
    }
}
