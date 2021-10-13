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
    public ResponseEntity<TurmaDTO> save(@RequestBody @Valid TurmaFormDTO form){
        return turmaService.save(form);
    }

    @GetMapping
    public ResponseEntity<Page<TurmaDTO>> findAll(@PageableDefault Pageable page){
        return ResponseEntity.ok(turmaService.findAll(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) { return turmaService.findById(id); }

    @GetMapping("/{id}/sprints")
    public ResponseEntity<List<SprintDTO>> findAllSprints(@PathVariable Long id){
        return turmaService.findTurmaSprints(id);
    }

    @PostMapping("/{id}/sprints")
    public ResponseEntity<?> addSprint(@PathVariable Long id, @RequestBody TurmaAddSprintFormDTO Form){
        return turmaService.addSprints(id ,Form);
    }



    @PutMapping("{/id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TurmaFormDTO form) {
        return turmaService.update(id, form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return turmaService.delete(id);
    }
}
