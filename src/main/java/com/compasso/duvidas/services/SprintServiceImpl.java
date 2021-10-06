package com.compasso.duvidas.services;

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


import java.util.Optional;

@Service
public class SprintServiceImpl implements SprintService{

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    @Transactional
    public ResponseEntity<SprintDTO> save(SprintFormDTO form) {
        Sprint entity = new Sprint();

        entity.setTitulo(form.getTitulo());

        //para cada cursoId passado no form o curso é pesquisado e se existir é adicionado na lista de cursos da sprint
        if (form.getCursosIds() != null) {
            for(Long cursoId : form.getCursosIds() ){
                Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
                if(cursoOptional.isPresent()){
                    entity.getCursos().add(cursoOptional.get());
                }
            }
        }

        sprintRepository.save(entity);
        SprintDTO sprintDTO = mapper.map(entity, SprintDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(sprintDTO);
    }

    @Override
    public Page<SprintDTO> findAll(Pageable page) {
        Page<Sprint> sprint = sprintRepository.findAll(page);
        Page<SprintDTO> sprintsDTOS = sprint.map(SprintDTO::new);
        return sprintsDTOS;
        }


    @Override
    public ResponseEntity<SprintDTO> findById(Long id) {
        Optional<Sprint> sprintOptional = sprintRepository.findById(id);
        if(sprintOptional.isPresent()){
            return ResponseEntity.ok().body(mapper.map(sprintOptional.get(), SprintDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public ResponseEntity<SprintDTO> update(Long id, SprintFormDTO form) {
        Optional<Sprint> sprintOptional = sprintRepository.findById(id);
        if(sprintOptional.isPresent()){
            Sprint entity = sprintOptional.get();
            entity.setTitulo(form.getTitulo());
            if (form.getCursosIds() != null) {
                for(Long cursoId : form.getCursosIds() ){
                    Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
                    if(cursoOptional.isPresent()){
                        entity.getCursos().add(cursoOptional.get());
                    }
                }
            }
            sprintRepository.save(entity);
            return ResponseEntity.ok().body(mapper.map(entity, SprintDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public ResponseEntity<SprintDTO> delete(Long id) {
        Optional<Sprint> sprintOptional = sprintRepository.findById(id);
        if(sprintOptional.isPresent()){
            sprintRepository.delete(sprintOptional.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
