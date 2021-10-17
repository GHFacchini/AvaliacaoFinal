package com.compasso.duvidas.controllers;

import com.compasso.duvidas.dto.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) { return usuarioService.findById(id); }

    @PutMapping("{/id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UsuarioFormDTO form) {
        return usuarioService.update(id, form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return usuarioService.delete(id);
    }

}
