package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.SprintDTO;
import com.compasso.duvidas.dto.SprintFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SprintService {

    ResponseEntity<SprintDTO> save(SprintFormDTO form);

    Page<SprintDTO> findAll(Pageable page);

    ResponseEntity<SprintDTO> findById(Long id);

    ResponseEntity<SprintDTO> update(Long id, SprintFormDTO form);

    ResponseEntity<SprintDTO> delete(Long id);

}
