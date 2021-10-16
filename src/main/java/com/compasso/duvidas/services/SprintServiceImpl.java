package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.CursoDTO;
import com.compasso.duvidas.dto.SprintDTO;
import com.compasso.duvidas.dto.SprintFormDTO;
import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.entities.Sprint;
import com.compasso.duvidas.repositories.CursoRepository;
import com.compasso.duvidas.repositories.SprintRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SprintServiceImpl implements SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    @Transactional
    public ResponseEntity<?> save(SprintFormDTO form) {
        Sprint entity = new Sprint();

        entity.setTitulo(form.getTitulo());

        //para cada cursoId passado no form o curso é pesquisado e se existir é adicionado na lista de cursos da sprint
        if (form.getCursosIds() != null) {
            for (Long cursoId : form.getCursosIds()) {
                Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
                if (!cursoOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não encontrada");
                }
                entity.getCursos().add(cursoOptional.get());
            }
        }

        sprintRepository.save(entity);
        SprintDTO sprintDTO = new SprintDTO(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(sprintDTO);
    }

    @Override
    public Page<SprintDTO> findAll(Pageable page) {
        Page<Sprint> sprint = sprintRepository.findAll(page);
        Page<SprintDTO> sprintsDTOS = sprint.map(SprintDTO::new);
        return sprintsDTOS;
    }


    @Override
    public ResponseEntity<?> findById(Long id) {
        Optional<Sprint> sprintOptional = sprintRepository.findById(id);
        if (!sprintOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não encontrada");
        }
        return ResponseEntity.ok().body(mapper.map(sprintOptional.get(), SprintDTO.class));

    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Long id, SprintFormDTO form) {
        Optional<Sprint> sprintOptional = sprintRepository.findById(id);
        if (!sprintOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não encontrada");
        }
        Sprint entity = sprintOptional.get();
        entity.setTitulo(form.getTitulo());
        if (form.getCursosIds() != null) {
            for (Long cursoId : form.getCursosIds()) {
                Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
                if (cursoOptional.isPresent()) {
                    entity.getCursos().add(cursoOptional.get());
                }
            }
        }
        sprintRepository.save(entity);
        return ResponseEntity.ok().body(mapper.map(entity, SprintDTO.class));

    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Optional<Sprint> sprintOptional = sprintRepository.findById(id);
        if (!sprintOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não encontrada");
        }
        sprintRepository.delete(sprintOptional.get());
        return ResponseEntity.ok().build();

    }

    //Cursos

    @Override
    @Transactional
    public ResponseEntity<?> addCurso(Long sprintId, Long CursoId) {
        Optional<Sprint> sprintOptional = sprintRepository.findById(sprintId);
        if (!sprintOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não encontrada");
        }
        Optional<Curso> cursoOptional = cursoRepository.findById(CursoId);
        if (!cursoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }
        if (sprintOptional.get().getCursos().contains(cursoOptional.get())) {
            return ResponseEntity.badRequest().body("Essa Sprint já possui esse curso");
        }
        sprintOptional.get().getCursos().add(cursoOptional.get());
        sprintRepository.save(sprintOptional.get());
        SprintDTO sprintDTO = new SprintDTO(sprintOptional.get());
        return ResponseEntity.ok().body(sprintDTO);
    }

    @Override
    public ResponseEntity<?> findCursos(Long sprintId) {
        Optional<Sprint> sprintOptional = sprintRepository.findById(sprintId);
        if (!sprintOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não encontrada");
        }
        List<CursoDTO> cursosDTOs = new ArrayList<>();
        for(Curso curso : sprintOptional.get().getCursos()){
            cursosDTOs.add(new CursoDTO(curso));
        }
        return ResponseEntity.ok().body(cursosDTOs);
    }

    @Override
    public ResponseEntity<?> removeCurso(Long sprintId, Long cursoId) {
        Optional<Sprint> sprintOptional = sprintRepository.findById(sprintId);
        if (!sprintOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não encontrada");
        }

        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (!cursoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }

        if (!sprintOptional.get().getCursos().contains(cursoOptional.get())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Essa sprint não contém essa sprint ");
        }

        sprintOptional.get().getCursos().remove(cursoOptional.get());
        sprintRepository.save(sprintOptional.get());

        return ResponseEntity.ok().body(new SprintDTO(sprintOptional.get()));
    }


}
