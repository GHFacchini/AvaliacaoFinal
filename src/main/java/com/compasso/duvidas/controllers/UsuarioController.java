package com.compasso.duvidas.controllers;

import com.compasso.duvidas.dto.TopicoDTO;
import com.compasso.duvidas.dto.TopicoFormDTO;
import com.compasso.duvidas.dto.UsuarioDTO;
import com.compasso.duvidas.dto.UsuarioFormDTO;
import com.compasso.duvidas.repositories.UsuarioRepository;
import com.compasso.duvidas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody @Valid UsuarioFormDTO form) {
        return usuarioService.save(form);
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> findAll(@PageableDefault Pageable page,
                                                    @RequestParam(required = false)String nome){
        return ResponseEntity.ok(usuarioService.findAll(page, nome));

    }

    /* adicionar aluno à uma determinada turma
    @GetMapping("/turmas/")
    public ResponseEntity<UsuarioDTO> adicionarEmTurma(){
    } */



}
