package com.compasso.duvidas.controllers;


import com.compasso.duvidas.dto.SprintDTO;
import com.compasso.duvidas.dto.SprintFormDTO;
import com.compasso.duvidas.services.SprintService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;


    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid SprintFormDTO form) {
        return sprintService.save(form);
    }

    @GetMapping
    public ResponseEntity<Page<SprintDTO>> findAll(@PageableDefault Pageable page) {
        return ResponseEntity.ok(sprintService.findAll(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return sprintService.findById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SprintFormDTO form) {
        return sprintService.update(id, form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return sprintService.delete(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não é possível deletar uma Sprint já cadastrada em uma turma");
        }
    }


    //cursos

    @PostMapping("/{id1}/cursos/{id2}")
    public ResponseEntity<?> addCurso(@PathVariable Long id1, @PathVariable Long id2) {
        return sprintService.addCurso(id1, id2);
    }

    @GetMapping("/{id}/cursos")
    public ResponseEntity<?> findCursos(@PathVariable Long id){
        return sprintService.findCursos(id);
    }

    @DeleteMapping("/{id1}/cursos/{id2}")
    public ResponseEntity<?> removeCursos(@PathVariable Long id1, @PathVariable Long id2) {
        return sprintService.removeCurso(id1, id2);
    }

}
