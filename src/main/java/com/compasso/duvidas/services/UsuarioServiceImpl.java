package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.UsuarioDTO;
import com.compasso.duvidas.dto.UsuarioFormDTO;
import com.compasso.duvidas.entities.Usuario;
import com.compasso.duvidas.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public UsuarioDTO save(UsuarioFormDTO form) {
        Usuario entity = usuarioRepository.save(mapper.map(form,  Usuario.class));
        return mapper.map(entity, UsuarioDTO.class);
    }

    @Override
    public Page<UsuarioDTO> findAll(Pageable page, String nome) {
        Page<Usuario> usuarios;
        if(nome != null) usuarios = usuarioRepository.findByNome(page, nome);
        else usuarios = usuarioRepository.findAll(page);
        Page<UsuarioDTO> usuariosDTOS = usuarios.map(UsuarioDTO::new);
        return usuariosDTOS;
    }

}
