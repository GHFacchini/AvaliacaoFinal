package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.TurmaDTO;
import com.compasso.duvidas.dto.TurmaFormDTO;


import com.compasso.duvidas.entities.Turma;
import com.compasso.duvidas.entities.Usuario;
import com.compasso.duvidas.repositories.TurmaRepository;
import com.compasso.duvidas.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TurmaServiceImpl implements TurmaService{


    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public TurmaDTO save(TurmaFormDTO form) {
        Turma entity = new Turma();
        entity.setNome(form.getNome());
        if(form.getUsuariosIds() != null){
            for(Long usuarioId : form.getUsuariosIds()){
                Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
                if(usuarioOptional.isPresent()){
                    entity.getUsuarios().add(usuarioOptional.get());
                    System.out.println(usuarioOptional.get());
                }
            }
        }
        turmaRepository.save(entity);
        return mapper.map(entity, TurmaDTO.class);
    }

    public Page<TurmaDTO> findAll(Pageable page){
        Page<Turma> turmas = turmaRepository.findAll(page);
        Page<TurmaDTO> turmasDTOS = turmas.map(TurmaDTO::new);
        return turmasDTOS;
    }

    @Override
    public ResponseEntity<TurmaDTO> findById(Long id) {
        Optional<Turma> turma = turmaRepository.findById(id);
        if(turma.isPresent()){
            return ResponseEntity.ok().body(mapper.map(turma.get(), TurmaDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    //falta adicionar os moderadores
    public ResponseEntity<TurmaDTO> update(Long id, TurmaFormDTO form) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if(turmaOptional.isPresent()){
            Turma entity = turmaOptional.get();
            entity.setNome(form.getNome());
            if(form.getUsuariosIds() != null){
                for(Long usuarioId : form.getUsuariosIds()){
                    Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
                    if(usuarioOptional.isPresent()){
                        entity.getUsuarios().add(usuarioOptional.get());
                        System.out.println(usuarioOptional.get());
                    }
                }
            }
            turmaRepository.save(entity);
            return ResponseEntity.ok().body(mapper.map(entity, TurmaDTO.class));
        }
        return ResponseEntity.notFound().build();


    }

    @Override
    @Transactional
    public ResponseEntity<TurmaDTO> delete(Long id) {
        Optional<Turma> turma = turmaRepository.findById(id);
        if(turma.isPresent()){
            turmaRepository.delete(turma.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
