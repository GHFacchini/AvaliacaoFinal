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
    public ResponseEntity<SprintDTO> save(@RequestBody @Valid SprintFormDTO form){
        return sprintService.save(form);
    }


    @GetMapping
    public ResponseEntity<Page<SprintDTO>> findAll(@PageableDefault Pageable page){
        return ResponseEntity.ok(sprintService.findAll(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintDTO> findById(@PathVariable Long id){ return sprintService.findById(id);}


    @PutMapping("/{id}")
    public ResponseEntity<SprintDTO> update(@PathVariable Long id, @RequestBody SprintFormDTO form){
        return sprintService.update(id, form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SprintDTO> delete(@PathVariable Long id){ return sprintService.delete(id);}

    @PostMapping("/{id1}/cursos/{id2}")
    public ResponseEntity<?> addSprint(@PathVariable Long id1, @PathVariable Long id2){
        return sprintService.addSprint(id1, id2);
    }

}
