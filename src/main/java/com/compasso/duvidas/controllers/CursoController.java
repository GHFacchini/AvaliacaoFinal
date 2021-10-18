package com.compasso.duvidas.controllers;

import javax.validation.Valid;

import com.compasso.duvidas.dto.*;
import com.compasso.duvidas.services.RespostaService;
import com.compasso.duvidas.services.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compasso.duvidas.services.CursoService;

@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    public ResponseEntity<CursoDTO> save(@RequestBody @Valid CursoFormDTO form) {
        return cursoService.save(form);
    }

    @GetMapping
    public ResponseEntity<Page<CursoDTO>> findAll(@PageableDefault Pageable page) {
        return ResponseEntity.ok(cursoService.findAll(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> findById(@PathVariable Long id) {
        return cursoService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> update(@PathVariable Long id, @RequestBody CursoFormDTO form) {
        return cursoService.update(id, form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return cursoService.delete(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não é possível deletar um curso já cadastrada em uma sprint");
        }
    }

    //Topico

    @PostMapping("/{id}/topicos")
    public ResponseEntity<?> saveTopico(@PathVariable Long id, @RequestBody @Valid TopicoFormDTO form) {
        return topicoService.save(id, form);
    }


   /* @GetMapping("/{id}/topicos")
    public ResponseEntity<?> findAllTopicos(@PathVariable Long id,
                                            @PageableDefault Pageable page,
                                            @RequestParam(required = false) String titulo,
                                            @RequestParam(required = false) String curso) {
        return topicoService.findAllFromCurso(id, page, titulo, curso);
    }
*/

    //faz o mesmo que o de cima mas não da pra filtrar por nome
    @GetMapping("/{id}/topicos")
    public ResponseEntity<?> findByCursoId(@PathVariable Long id,@PageableDefault Pageable page){
        return topicoService.findByCursoId(page, id);
    }

    @GetMapping("/{id1}/topicos/{id2}")
    public ResponseEntity<?> findTopicoById(@PathVariable Long id1, @PathVariable Long id2) {
        return topicoService.findById(id1, id2);
    }


    @PutMapping("/{id1}/topicos/{id2}")
    public ResponseEntity<?> updateTopico(@PathVariable Long id1, @PathVariable Long id2, @RequestBody TopicoFormDTO form) {
        return topicoService.update(id1, id2, form);
    }

    @PatchMapping("{id1}/topicos/{id2}/fechar")
    public ResponseEntity<?> closeTopico(@PathVariable Long id1, @PathVariable Long id2) {
        return topicoService.close(id1, id1);
    }


    @DeleteMapping("/{id1}/topicos/{id2}")
    public ResponseEntity<?> delete(@PathVariable Long id1, @PathVariable Long id2) {
        return topicoService.delete(id1, id2);
    }


    //Respostas

    @PostMapping("/{id1}/topicos/{id2}/respostas")
    public ResponseEntity<?> save(@PathVariable Long id1, @PathVariable Long id2, @RequestBody @Valid RespostaFormDTO form) {
        return respostaService.save(id1, id2, form);
    }



    @GetMapping("/{id1}/topicos/{id2}/respostas/{id3}")
    public ResponseEntity<?> findById(@PathVariable Long id1, @PathVariable Long id2, @PathVariable Long id3) {
        return respostaService.findById(id1, id2, id3);
    }

    @PutMapping("/{id1}/topicos/{id2}/respostas/{id3}")
    public ResponseEntity<?> update(@PathVariable Long id1,@PathVariable Long id2, @PathVariable Long id3, @RequestBody RespostaFormDTO form) {
        return respostaService.update(id1, id2, id3, form);
    }


    @PatchMapping("/{id1}/topicos/{id2}/respostas/{id3}")
    public ResponseEntity<?> setSolucao(@PathVariable Long id1,@PathVariable Long id2, @PathVariable Long id3){
        return respostaService.setSolucao(id1, id2, id3);
    }

    @DeleteMapping("/{id1}/topicos/{id2}/respostas/{id3}")
    public ResponseEntity<?> delete(@PathVariable Long id1,@PathVariable Long id2, @PathVariable Long id3) {
        return respostaService.delete(id1, id2, id3);
    }




}
