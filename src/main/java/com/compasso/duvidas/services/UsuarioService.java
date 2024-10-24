package com.compasso.duvidas.services;


import com.compasso.duvidas.dto.UsuarioDTO;
import com.compasso.duvidas.dto.UsuarioFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {

    ResponseEntity<?> save(UsuarioFormDTO form);

    Page<UsuarioDTO> findAll(Pageable page, String nome);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> update(Long id, UsuarioFormDTO form);

    ResponseEntity<?> delete(Long id);

}
