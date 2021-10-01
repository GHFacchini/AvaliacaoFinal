package com.compasso.duvidas.services;


import com.compasso.duvidas.dto.UsuarioDTO;
import com.compasso.duvidas.dto.UsuarioFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {

    ResponseEntity<UsuarioDTO> save(UsuarioFormDTO form);

    Page<UsuarioDTO> findAll(Pageable page, String nome);

    ResponseEntity<UsuarioDTO> findById(Long id);

    ResponseEntity<UsuarioDTO> update(Long id, UsuarioFormDTO form);

    ResponseEntity<UsuarioDTO> delete(Long id);

}
