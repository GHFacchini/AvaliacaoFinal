package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TurmaService {

    ResponseEntity<TurmaDTO> save(TurmaFormDTO form);

    Page<TurmaDTO> findAll(Pageable page);

    ResponseEntity<TurmaDTO> findById(Long id);

    ResponseEntity<TurmaDTO> update(Long id,TurmaFormDTO form);

    ResponseEntity<TurmaDTO> delete(Long id);

    ResponseEntity<List<SprintDTO>> findTurmaSprints(Long id);

    ResponseEntity<?> addSprints(Long id, TurmaAddSprintFormDTO form);

    ResponseEntity<?> addUsuario(TurmaAddUsuarioFormDTO form);

}
