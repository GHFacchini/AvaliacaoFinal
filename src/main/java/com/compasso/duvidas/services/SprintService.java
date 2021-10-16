package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.SprintDTO;
import com.compasso.duvidas.dto.SprintFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SprintService {

    ResponseEntity<?> save(SprintFormDTO form);

    Page<SprintDTO> findAll(Pageable page);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> update(Long id, SprintFormDTO form);

    ResponseEntity<?> delete(Long id);

    ResponseEntity<?> addCurso(Long id1, Long id2);

    ResponseEntity<?> findCursos(Long id);

    ResponseEntity<?> removeCurso(Long id1, Long id2);
}
