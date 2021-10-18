package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.*;


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
import java.util.ArrayList;
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
    public ResponseEntity<?> save(TurmaFormDTO form) {
        Turma entity = new Turma();
        entity.setNome(form.getNome());
        if (form.getUsuariosIds() != null) {
            for (Long usuarioId : form.getUsuariosIds()) {

                Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
                if (!usuarioOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
                }

                entity.getUsuarios().add(usuarioOptional.get());
                turmaRepository.save(entity);

                usuarioOptional.get().getTurmas().add(entity);
                usuarioRepository.save(usuarioOptional.get());

            }
            TurmaDTO turmaDTO = new TurmaDTO(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(turmaDTO);
        }
        turmaRepository.save(entity);
        TurmaDTO turmaDTO = new TurmaDTO(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaDTO);
    }


    public Page<TurmaDTO> findAll(Pageable page) {
        Page<Turma> turmas = turmaRepository.findAll(page);
        Page<TurmaDTO> turmasDTOS = turmas.map(TurmaDTO::new);
        return turmasDTOS;
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if (!turmaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
        }
        TurmaDTO turmaDTO = new TurmaDTO(turmaOptional.get());
        return ResponseEntity.ok().body(turmaDTO);

    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Long id, TurmaFormDTO form) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if (!turmaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
        }
        Turma entity = turmaOptional.get();
        entity.setNome(form.getNome());
        if (form.getUsuariosIds() != null) {
            entity.getUsuarios().clear();
            for (Long usuarioId : form.getUsuariosIds()) {
                Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
                if (!usuarioOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
                }
                entity.getUsuarios().add(usuarioOptional.get());
                System.out.println(usuarioOptional.get());

            }
        }
        turmaRepository.save(entity);
        return ResponseEntity.ok().body(new TurmaDTO((entity)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if (!turmaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
        }
        if (!turmaOptional.get().getUsuarios().isEmpty()) {
            for (Usuario usuario : turmaOptional.get().getUsuarios()) {
                usuario.getTurmas().remove(turmaOptional.get());
                usuarioRepository.save(usuario);
            }
            turmaOptional.get().getUsuarios().clear();
        }
        turmaRepository.delete(turmaOptional.get());
        return ResponseEntity.ok().build();


    }

    //Sprints

    @Override
    @Transactional
    public ResponseEntity<?> addSprints(Long id, TurmaAddSprintFormDTO form) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if (!turmaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
        }
        Turma entity = turmaOptional.get();

        for (Long sprintId : form.getSprintsIds()) {
            Optional<Sprint> sprintOptional = sprintRepository.findById(sprintId);
            if (!sprintOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não encontrada");
            }
            if ((turmaOptional.get().getSprints().contains(sprintOptional.get()))) {
                return ResponseEntity.badRequest().body("Essa turma já possui a sprint de id:" + sprintOptional.get().getId());
            }
            entity.getSprints().add(sprintOptional.get());

        }

        turmaRepository.save(entity);
        TurmaDTO turmaDTO = new TurmaDTO(entity);
        return ResponseEntity.ok().body(turmaDTO);
    }

    @Override
    public ResponseEntity<?> findTurmaSprints(Long id) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if (!turmaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
        }

        Turma turma = turmaOptional.get();
        List<Sprint> sprints = turma.getSprints();
        List<SprintDTO> sprintsDTOS = sprints.stream().map(SprintDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(sprintsDTOS);
    }

    @Override
    public ResponseEntity<?> removeSprint(Long turmaId, Long sprintId) {
        Optional<Turma> turmaOptional = turmaRepository.findById(turmaId);
        if (!turmaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
        }

        Optional<Sprint> sprintOptional = sprintRepository.findById(sprintId);
        if (!sprintOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não encontrada");
        }

        if (!turmaOptional.get().getSprints().contains(sprintOptional.get())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Essa turma não contém essa sprint ");
        }

        turmaOptional.get().getSprints().remove(sprintOptional.get());
        turmaRepository.save(turmaOptional.get());

        return ResponseEntity.ok().body(new TurmaDTO(turmaOptional.get()));

    }


    //Usuarios

    @Override
    @Transactional
    public ResponseEntity<?> addUsuario(Long turmaId, Long usuarioId) {
        Optional<Turma> turmaOptional = turmaRepository.findById(turmaId);
        if (!turmaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
        }
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrada");
        }
        if (turmaOptional.get().getUsuarios().contains(usuarioOptional.get())) {
            return ResponseEntity.badRequest().body("Esse usuário já está nessa turma");
        }

        turmaOptional.get().getUsuarios().add(usuarioOptional.get());
        turmaRepository.save(turmaOptional.get());

        usuarioOptional.get().getTurmas().add(turmaOptional.get());
        usuarioRepository.save(usuarioOptional.get());

        return ResponseEntity.ok().body(new TurmaDTO(turmaOptional.get()));

    }

    @Override
    public ResponseEntity<?> findUsuarios(Long turmaId) {
        Optional<Turma> turmaOptional = turmaRepository.findById(turmaId);
        if (!turmaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
        }

        List<UsuarioDTO> usuariosDTOs = new ArrayList<>();
        for (Usuario usuario : turmaOptional.get().getUsuarios()) {
            usuariosDTOs.add(new UsuarioDTO(usuario));
        }
        return ResponseEntity.ok().body(usuariosDTOs);

    }


    @Override
    @Transactional
    public ResponseEntity<?> removeUsuario(Long turmaId, Long usuarioId) {
        Optional<Turma> turmaOptional = turmaRepository.findById(turmaId);
        if (!turmaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrada");
        }

        if (!turmaOptional.get().getUsuarios().contains(usuarioOptional.get())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O usuário não pertence a essa turma");
        }

        turmaOptional.get().getUsuarios().remove(usuarioOptional.get());
        turmaRepository.save(turmaOptional.get());

        usuarioOptional.get().getTurmas().remove(turmaOptional.get());
        usuarioRepository.save(usuarioOptional.get());

        return ResponseEntity.ok().body(new TurmaDTO(turmaOptional.get()));
    }


}




