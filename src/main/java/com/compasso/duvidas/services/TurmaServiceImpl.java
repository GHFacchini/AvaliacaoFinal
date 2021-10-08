package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.SprintDTO;
import com.compasso.duvidas.dto.TurmaAddSprintFormDTO;
import com.compasso.duvidas.dto.TurmaDTO;
import com.compasso.duvidas.dto.TurmaFormDTO;


import com.compasso.duvidas.entities.Sprint;
import com.compasso.duvidas.entities.Turma;
import com.compasso.duvidas.entities.Usuario;
import com.compasso.duvidas.repositories.SprintRepository;
import com.compasso.duvidas.repositories.TurmaRepository;
import com.compasso.duvidas.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaServiceImpl implements TurmaService {


    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public ResponseEntity<TurmaDTO> save(TurmaFormDTO form) {
        Turma entity = new Turma();
        entity.setNome(form.getNome());
        if (form.getUsuariosIds() != null) {
            for (Long usuarioId : form.getUsuariosIds()) {
                Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
                if (usuarioOptional.isPresent()) {
                    entity.getUsuarios().add(usuarioOptional.get());
                }
            }
        }
        turmaRepository.save(entity);
        TurmaDTO turmaDTO = mapper.map(entity, TurmaDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaDTO);
    }


    public Page<TurmaDTO> findAll(Pageable page) {
        Page<Turma> turmas = turmaRepository.findAll(page);
        Page<TurmaDTO> turmasDTOS = turmas.map(TurmaDTO::new);
        return turmasDTOS;
    }

    @Override
    public ResponseEntity<TurmaDTO> findById(Long id) {
        Optional<Turma> turma = turmaRepository.findById(id);
        if (turma.isPresent()) {
            return ResponseEntity.ok().body(mapper.map(turma.get(), TurmaDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    //falta adicionar os moderadores
    public ResponseEntity<TurmaDTO> update(Long id, TurmaFormDTO form) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if (turmaOptional.isPresent()) {
            Turma entity = turmaOptional.get();
            entity.setNome(form.getNome());
            if (form.getUsuariosIds() != null) {
                for (Long usuarioId : form.getUsuariosIds()) {
                    Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
                    if (usuarioOptional.isPresent()) {
                        entity.getUsuarios().add(usuarioOptional.get());
                        System.out.println(usuarioOptional.get());
                    }
                }
            }
            turmaRepository.save(entity);
            return ResponseEntity.ok().body(mapper.map(entity, TurmaDTO.class));
        }
        return ResponseEntity.notFound().build();


    }

    @Override
    @Transactional
    public ResponseEntity<TurmaDTO> delete(Long id) {
        Optional<Turma> turma = turmaRepository.findById(id);
        if (turma.isPresent()) {
            turmaRepository.delete(turma.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<SprintDTO>> findTurmaSprints(Long id) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if (turmaOptional.isPresent()) {
            Turma turma = turmaOptional.get();
            List<Sprint> sprints = turma.getSprints();
            List<SprintDTO> sprintsDTOS = sprints.stream().map(SprintDTO::new).collect(Collectors.toList());
            return ResponseEntity.ok().body(sprintsDTOS);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<TurmaDTO> addSprints(Long id, TurmaAddSprintFormDTO form) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if (turmaOptional.isPresent()) {
            Turma entity = turmaOptional.get();
            if (form.getSprintsIds() != null) {
                for (Long sprintId : form.getSprintsIds()) {
                    Optional<Sprint> sprintOptional = sprintRepository.findById(sprintId);
                    if (sprintOptional.isPresent()) {
                        entity.getSprints().add(sprintOptional.get());
                        System.out.println(sprintOptional.get());
                    }
                }
            }
            turmaRepository.save(entity);
            return ResponseEntity.ok().body(mapper.map(entity, TurmaDTO.class));
        }
        return ResponseEntity.notFound().build();
    }
}




