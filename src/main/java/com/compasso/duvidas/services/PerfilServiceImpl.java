package com.compasso.duvidas.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.aspectj.util.GenericSignature.ArrayTypeSignature;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.compasso.duvidas.dto.PerfilDTO;
import com.compasso.duvidas.dto.PerfilFormDTO;
import com.compasso.duvidas.entities.Perfil;
import com.compasso.duvidas.repositories.PerfilRepository;

@Service
public class PerfilServiceImpl implements PerfilService{

	@Autowired
    private ModelMapper mapper;
	
	@Autowired
    private PerfilRepository perfilRepository;
	
	@Override
	public ResponseEntity<?> save(@Valid PerfilFormDTO form) {
		Perfil entity = new Perfil();
        entity.setNome(form.getNome());
        perfilRepository.save(entity);
        PerfilDTO perfilDTO = new PerfilDTO(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilDTO);
	}

	@Override
	public Page<PerfilDTO> findAll(Pageable page) {
		Page<Perfil> perfis = perfilRepository.findAll(page);
        Page<PerfilDTO> perfisDTOS = perfis.map(PerfilDTO::new);
        return perfisDTOS;
        }

	@Override
	@Transactional
	public ResponseEntity<?> update(Long id, PerfilFormDTO form) {
		Optional<Perfil> perfilOptional = perfilRepository.findById(id);
        if(perfilOptional.isPresent()){
        	Perfil entity = perfilOptional.get();
            entity.setNome(form.getNome());
            perfilRepository.save(entity);
            return ResponseEntity.ok().body(mapper.map(entity, PerfilDTO.class));
        }
        return ResponseEntity.notFound().build();
	}

	@Override
	@Transactional
	public ResponseEntity<?> delete(Long id) {
		Optional<Perfil> perfilOptional = perfilRepository.findById(id);
        if(perfilOptional.isPresent()){
            perfilRepository.delete(perfilOptional.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
	}
	

}
