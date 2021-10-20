package com.compasso.duvidas.controllers;

import com.compasso.duvidas.dto.*;
import com.compasso.duvidas.services.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid TurmaFormDTO form){
        return turmaService.save(form);
    }

    @GetMapping
    public ResponseEntity<Page<TurmaDTO>> findAll(@PageableDefault Pageable page){
        return ResponseEntity.ok(turmaService.findAll(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) { return turmaService.findById(id); }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TurmaFormDTO form) {
        return turmaService.update(id, form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return turmaService.delete(id);
    }

    //sprints

    @PostMapping("/{id}/sprints")
    public ResponseEntity<?> addSprint(@PathVariable Long id, @RequestBody @Valid TurmaAddSprintFormDTO form){
        return turmaService.addSprints(id ,form);
    }

    @GetMapping("/{id}/sprints")
    public ResponseEntity<?> findAllSprints(@PathVariable Long id){
        return turmaService.findTurmaSprints(id);
    }

    @DeleteMapping("/{id1}/sprints/{id2}")
    public ResponseEntity<?> removeSprint(@PathVariable Long id1, @PathVariable Long id2){
        return turmaService.removeSprint(id1, id2);
    }

    //usuarios

    @PostMapping("/{id1}/usuarios/{id2}")
    public ResponseEntity<?> addUsuario(@PathVariable Long id1,@PathVariable Long id2){
        return turmaService.addUsuario(id1, id2);
    }

    @GetMapping("/{id1}/usuarios")
    public ResponseEntity<?> findUsuarios(@PathVariable Long id1){
        return turmaService.findUsuarios(id1);
    }

    @DeleteMapping("/{id1}/usuarios/{id2}")
    public ResponseEntity<?> removeUsuario(@PathVariable Long id1,@PathVariable Long id2){
        return turmaService.removeUsuario(id1, id2);
    }




}
