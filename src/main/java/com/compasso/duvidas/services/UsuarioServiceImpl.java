package com.compasso.duvidas.services;

import com.compasso.duvidas.dto.UsuarioDTO;
import com.compasso.duvidas.dto.UsuarioFormDTO;
import com.compasso.duvidas.entities.Turma;
import com.compasso.duvidas.entities.Usuario;
import com.compasso.duvidas.repositories.TurmaRepository;
import com.compasso.duvidas.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public ResponseEntity<UsuarioDTO> save(UsuarioFormDTO form) {
        Usuario entity = new Usuario();
            entity.setNome(form.getNome());
            entity.setEmail(form.getEmail());
            entity.setSenha(form.getSenha());
            entity.setTipoUsuario(form.getTipoUsuario());
            if(form.getTurmaId() != null){
                Optional<Turma> turmaOptional = turmaRepository.findById(form.getTurmaId());
                if(turmaOptional.isPresent()) {
                    entity.setTurma(turmaOptional.get());
                    usuarioRepository.save(entity);

                    Turma turma = turmaOptional.get();
                    turma.adicionarUsuario(entity);
                    turmaRepository.save(turma);
                }
            }else{
                usuarioRepository.save(entity);
            }

            UsuarioDTO usuarioDTO = mapper.map(entity, UsuarioDTO.class);


            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);


    }

    @Override
    public Page<UsuarioDTO> findAll(Pageable page, String nome) {
        Page<Usuario> usuarios;
        if(nome != null) usuarios = usuarioRepository.findByNome(page, nome);
        else usuarios = usuarioRepository.findAll(page);
        Page<UsuarioDTO> usuariosDTOS = usuarios.map(UsuarioDTO::new);
        return usuariosDTOS;
    }

    @Override
    public ResponseEntity<UsuarioDTO> findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()){
            return ResponseEntity.ok().body(mapper.map(usuario.get(), UsuarioDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioDTO> update(Long id, UsuarioFormDTO form) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            Usuario entity = usuario.get();
            if(form.getNome() != null)
                entity.setNome(form.getNome());
            if(form.getTurmaId() != null){
                Optional<Turma> turmaOptional = turmaRepository.findById(form.getTurmaId());
                if(turmaOptional.isPresent()) {
                    entity.setTurma(turmaOptional.get());

                    Turma turma = turmaOptional.get();
                    turma.adicionarUsuario(entity);
                    turmaRepository.save(turma);
                }
            }
            UsuarioDTO usuarioDTO = mapper.map(entity, UsuarioDTO.class);

            usuarioRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
        }
        return ResponseEntity.notFound().build();

    }

    @Override
    public ResponseEntity<UsuarioDTO> delete(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()){
            usuarioRepository.delete(usuario.get());
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}
