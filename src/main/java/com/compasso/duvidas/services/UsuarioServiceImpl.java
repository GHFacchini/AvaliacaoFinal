package com.compasso.duvidas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compasso.duvidas.dto.UsuarioDTO;
import com.compasso.duvidas.dto.UsuarioFormDTO;
import com.compasso.duvidas.entities.Perfil;
import com.compasso.duvidas.entities.Turma;
import com.compasso.duvidas.entities.Usuario;
import com.compasso.duvidas.repositories.PerfilRepository;
import com.compasso.duvidas.repositories.TurmaRepository;
import com.compasso.duvidas.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TurmaRepository turmaRepository;
    
    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public ResponseEntity<UsuarioDTO> save(UsuarioFormDTO form) {
    	Optional<Perfil> perfilOptional;
    	if(form.getPerfilId() != null) {
    		perfilOptional = perfilRepository.findById(form.getPerfilId());
    	} else {
    		perfilOptional = perfilRepository.findByNome("ROLE_BOLSISTA");
    	}
    	
    	List<Perfil> perfil = new ArrayList<>();
    	perfil.add(perfilOptional.get());
    	
        Usuario entity = new Usuario();
            entity.setNome(form.getNome());
            entity.setEmail(form.getEmail());
            entity.setSenha(form.getSenha());
            entity.setPerfis(perfil);
            if(form.getTurmasIds() != null){
                for(Long turmaId: form.getTurmasIds()){
                    Optional<Turma> turmasOptional = turmaRepository.findById(turmaId);
                    if(turmasOptional.isPresent()) {
                        entity.getTurmas().add((turmasOptional.get()));
                }

                    usuarioRepository.save(entity);

                    Turma turma = turmasOptional.get();
                    turma.adicionarUsuario(entity);
                    turmaRepository.save(turma);
                }
            }else{
                usuarioRepository.save(entity);
            }

            UsuarioDTO usuarioDTO = new UsuarioDTO(entity);


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
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok().body(new UsuarioDTO(usuarioOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Long id, UsuarioFormDTO form) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            Usuario entity = usuario.get();
            if(form.getNome() != null)
                entity.setNome(form.getNome());
            if(form.getPerfilId() != null) {
            	Optional<Perfil> perfilOptional;
            	perfilOptional = perfilRepository.findById(form.getPerfilId());
            	
            	if(perfilOptional.isPresent()) entity.getPerfis().add(perfilOptional.get());
            	else return ((BodyBuilder) ResponseEntity.notFound()).body("Perfil '" + form.getPerfilId() + "' não encontrado");
            }
            if(form.getTurmasIds() != null) {
                for(Long turmaId: form.getTurmasIds()) {
                    Optional<Turma> turmasOptional = turmaRepository.findById(turmaId);
                    if(turmasOptional.isPresent()) {
                        entity.getTurmas().add((turmasOptional.get()));
                    }

                    usuarioRepository.save(entity);

                    Turma turma = turmasOptional.get();
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
    @Transactional
    public ResponseEntity<UsuarioDTO> delete(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isPresent()){
            if(!usuarioOptional.get().getTurmas().isEmpty()){
                for(Turma turma : usuarioOptional.get().getTurmas()){
                    turma.getUsuarios().remove(usuarioOptional.get());
                    turmaRepository.save(turma);
                }
                usuarioOptional.get().getTurmas().clear();
            }
            usuarioRepository.delete(usuarioOptional.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
