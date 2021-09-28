package com.compasso.duvidas.services;


import com.compasso.duvidas.dto.UsuarioDTO;
import com.compasso.duvidas.dto.UsuarioFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    UsuarioDTO save(UsuarioFormDTO form);

    Page<UsuarioDTO> findAll(Pageable page, String nome);
}
