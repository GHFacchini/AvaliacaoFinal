package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TurmaService {

    ResponseEntity<?> save(TurmaFormDTO form);

    Page<TurmaDTO> findAll(Pageable page);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> update(Long id,TurmaFormDTO form);

    ResponseEntity<?> delete(Long id);

    ResponseEntity<?> findTurmaSprints(Long id);

    ResponseEntity<?> addSprints(Long id, TurmaAddSprintFormDTO form);

    ResponseEntity<?> addUsuario(Long turmaId, Long usuarioId);

    ResponseEntity<?> removeUsuario(Long turmaId, Long usuarioId);

    ResponseEntity<?> findUsuarios(Long id1);

    ResponseEntity<?> removeSprint(Long turmaId, Long sprintId);
}
