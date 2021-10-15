package com.compasso.duvidas.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.compasso.duvidas.dto.TopicoDTO;
import com.compasso.duvidas.dto.TopicoFormDTO;
import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.entities.Resposta;
import com.compasso.duvidas.entities.Topico;
import com.compasso.duvidas.entities.Usuario;
import com.compasso.duvidas.repositories.CursoRepository;
import com.compasso.duvidas.repositories.RespostaRepository;
import com.compasso.duvidas.repositories.TopicoRepository;
import com.compasso.duvidas.repositories.UsuarioRepository;

@Service
public class TopicoServiceImpl implements TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public ResponseEntity<TopicoDTO> save(TopicoFormDTO form) {
        Topico entity = new Topico();
        Optional<Usuario> autorOptional = usuarioRepository.findById(form.getAutorId());
        Optional<Curso> cursoOptional = cursoRepository.findById(form.getCursoId());

        if (autorOptional.isPresent() && cursoOptional.isPresent()) {
            entity.setTitulo(form.getTitulo());
            entity.setDescricao(form.getDescricao());
            entity.setAutor(autorOptional.get());
            entity.setCurso(cursoOptional.get());

            topicoRepository.save(entity);
            TopicoDTO topicoDTO = new TopicoDTO(entity);

            Curso curso = cursoOptional.get();
            curso.getTopicos().add(entity);
            cursoRepository.save(curso);

            return ResponseEntity.status(HttpStatus.CREATED).body(topicoDTO);
        }
        return ResponseEntity.notFound().build();

    }

    @Override
    public ResponseEntity<Page<TopicoDTO>> findAll(Pageable page, String titulo, String curso) {
        Page<Topico> topicos;
        if (titulo != null) {
            topicos = topicoRepository.findByTituloLike(page, titulo);
            System.out.println(topicos.getTotalElements());
        } else if(curso != null){
        	topicos = topicoRepository.findByCursoNome(page, curso);
        } else topicos = topicoRepository.findAll(page);
        
        Page<TopicoDTO> topicosDTOS = topicos.map(TopicoDTO::new);
        return ResponseEntity.ok().body(topicosDTOS);
    }

    @Override
    @Transactional
    public ResponseEntity<?> close(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            Topico topicoFechado = topico.get();
            topicoFechado.close();
            topicoRepository.save(topicoFechado);
            return ResponseEntity.ok().body("Tópico '" + topicoFechado.getTitulo()
                    + "' (ID: " + topicoFechado.getId() + ") foi FECHADO!");
        } else
            return ResponseEntity.notFound().build();
    }


    @Override
    public ResponseEntity<TopicoDTO> findById(Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            return ResponseEntity.ok().body(new TopicoDTO(topicoOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<TopicoDTO> update(Long id, TopicoFormDTO form) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            Topico entity = topico.get();
            if (form.getTitulo() != null) {
                entity.setTitulo(form.getTitulo());
            }
            if (form.getDescricao() != null) {
                entity.setDescricao(form.getDescricao());
            }

            topicoRepository.save(entity);

            return ResponseEntity.ok().body(mapper.map(entity, TopicoDTO.class));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    @Transactional
    public ResponseEntity<TopicoDTO> delete(Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (!topicoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        for (Resposta resposta : topicoOptional.get().getRespostas()) {
            respostaRepository.delete(resposta);
        }
        //remove o tópico da lista de tópicos do curso
        topicoOptional.get().getCurso().getTopicos().remove(topicoOptional.get());
        cursoRepository.save(topicoOptional.get().getCurso());
        topicoRepository.delete(topicoOptional.get());
        return ResponseEntity.ok().build();
    }

}
