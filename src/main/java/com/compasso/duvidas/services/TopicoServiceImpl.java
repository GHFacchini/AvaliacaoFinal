package com.compasso.duvidas.services;

import java.util.ArrayList;
import java.util.List;
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


    //salva o tópico em um curso
    @Override
    @Transactional
    public ResponseEntity<?> save(Long id, TopicoFormDTO form) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        Optional<Usuario> autorOptional = usuarioRepository.findById(form.getAutorId());
        ResponseEntity<?> response = verificaOptionalComAutor(cursoOptional, autorOptional);
        if(response != null){
            return response;
        }

        Topico entity = new Topico();
        entity.setTitulo(form.getTitulo());
        entity.setDescricao(form.getDescricao());
        entity.setAutor(autorOptional.get());
        entity.setCurso(cursoOptional.get());

        topicoRepository.save(entity);

        Curso curso = cursoOptional.get();
        curso.getTopicos().add(entity);
        cursoRepository.save(curso);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TopicoDTO(entity));
    }

    //deve buscar todos os tópicos daquele curso com paginação e filtros
    //não sei como buscar dentro do tópico com os filtros provavelmente tem que montar a Query manualmente
    @Override
    public ResponseEntity<?> findAllFromCurso(Long id, Pageable page, String titulo, String Curso) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        /*if (!cursoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }
        List<TopicoDTO> topicosDTOs = new ArrayList<>();
        for(Topico topico : cursoOptional.get().getTopicos()){
            topicosDTOs.add(new TopicoDTO(topico));
        }
        return ResponseEntity.ok().body(topicosDTOs);*/
        return null;
    }

    //busca por um ID especifico em um curso
    @Override
    public ResponseEntity<?> findById(Long cursoId, Long topicoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        Optional<Topico> topicoOptional = topicoRepository.findById(topicoId);
        ResponseEntity<?> response = verificaOptional(cursoOptional, topicoOptional);
        if(response !=null ){
            return response;
        }

        return ResponseEntity.ok().body(new TopicoDTO(topicoOptional.get()));
    }

    @Override
    @Transactional
    public ResponseEntity<?> close(Long cursoId, Long topicoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        Optional<Topico> topicoOptional = topicoRepository.findById(topicoId);
        ResponseEntity<?> response = verificaOptional(cursoOptional, topicoOptional);
        if(response !=null ){
            return response;
        }

        Topico topicoFechado = topicoOptional.get();
        topicoFechado.close();
        topicoRepository.save(topicoFechado);
        return ResponseEntity.ok().body("Tópico '" + topicoFechado.getTitulo()
                + "' (ID: " + topicoFechado.getId() + ") foi FECHADO!");

    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long cursoId, Long topicoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        Optional<Topico> topicoOptional = topicoRepository.findById(topicoId);
        ResponseEntity<?> response = verificaOptional(cursoOptional, topicoOptional);
        if(response !=null ){
            return response;
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

    @Override
    public ResponseEntity<?> update(Long cursoId, Long topicoId, TopicoFormDTO form) {
        return null;
    }



    //buscar todos os tópicos em todos os cursos
    @Override
    public ResponseEntity<Page<TopicoDTO>> findAll(Pageable page, String titulo, String curso) {
        Page<Topico> topicos;
        if (titulo != null) {
            topicos = topicoRepository.findByTituloLike(page, titulo);
        } else if (curso != null) {
            topicos = topicoRepository.findByCursoNome(page, curso);
        } else topicos = topicoRepository.findAll(page);

        Page<TopicoDTO> topicosDTOS = topicos.map(TopicoDTO::new);
        return ResponseEntity.ok().body(topicosDTOS);
    }


    //Utilitário

    private ResponseEntity<?> verificaOptional(Optional<Curso> curso, Optional<Topico> topico) {
        if (!curso.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }
        if ((!topico.isPresent() || !curso.get().getTopicos().contains(topico.get()))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topico não encontrado");
        }
        return null;
    }

    private ResponseEntity<?> verificaOptionalComAutor(Optional<Curso> curso, Optional<Usuario> autor) {
        if (!curso.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }
        if (!autor.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return null;
    }

}
