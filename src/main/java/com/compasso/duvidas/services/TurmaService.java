package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.TurmaDTO;
import com.compasso.duvidas.dto.TurmaFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TurmaService {

    TurmaDTO save(TurmaFormDTO form);

    Page<TurmaDTO> findAll(Pageable page);

    ResponseEntity<TurmaDTO> findById(Long id);

    ResponseEntity<TurmaDTO> update(Long id,TurmaFormDTO form);

    ResponseEntity<TurmaDTO> delete(Long id);
}
