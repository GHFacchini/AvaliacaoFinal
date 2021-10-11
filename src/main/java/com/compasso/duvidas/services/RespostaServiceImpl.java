package com.compasso.duvidas.services;

import java.util.List;
import java.util.Optional;

import com.compasso.duvidas.constants.StatusTopico;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Service;

import com.compasso.duvidas.dto.RespostaDTO;
import com.compasso.duvidas.dto.RespostaFormDTO;
import com.compasso.duvidas.entities.Resposta;
import com.compasso.duvidas.entities.Topico;
import com.compasso.duvidas.entities.Usuario;
import com.compasso.duvidas.repositories.RespostaRepository;
import com.compasso.duvidas.repositories.TopicoRepository;
import com.compasso.duvidas.repositories.UsuarioRepository;

@Service
public class RespostaServiceImpl implements RespostaService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public ResponseEntity<?> save(RespostaFormDTO form) {

        Optional<Topico> topicoOptional = topicoRepository.findById(form.getIdTopico());
        //se não acha o tópcio retorna not found
        if (topicoOptional.isPresent()) {

            //se o tópico está fechado retorna bad request
            if (topicoOptional.get().getStatus() == StatusTopico.FECHADO) {
                return ResponseEntity.badRequest().body("O tópico está fechado");
            }

            Optional<Usuario> autorOptional = usuarioRepository.findById(form.getIdAutor());
            //se tudo der certo retorna created com o DTO, se não retorna bad request
            if (autorOptional.isPresent()) {
                Resposta entity = new Resposta();
                entity.setAutor(autorOptional.get());
                entity.setTopico(topicoOptional.get());
                entity.setMensagem(form.getMensagem());

                List<Resposta> respostas = topicoOptional.get().getRespostas();
                respostas.add(entity);
                if (topicoOptional.get().getStatus() == StatusTopico.NAO_RESPONDIDO) {
                    topicoOptional.get().setStatus(StatusTopico.NAO_SOLUCIONADO);
                }
                topicoOptional.get().setRespostas(respostas);

                respostaRepository.save(entity);
                topicoRepository.save(topicoOptional.get());

                RespostaDTO respostaDTO = mapper.map(entity, RespostaDTO.class);

                return ResponseEntity.status(HttpStatus.CREATED).body(respostaDTO);
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }


    @Override
    public Page<RespostaDTO> findAll(Pageable page) {
        Page<Resposta> respostas = respostaRepository.findAll(page);
        Page<RespostaDTO> respostasDTOS = respostas.map(RespostaDTO::new);
        return respostasDTOS;
    }

    @Override
    public ResponseEntity<RespostaDTO> findById(Long id) {
        Optional<Resposta> resposta = respostaRepository.findById(id);
        if (resposta.isPresent()) {
            return ResponseEntity.ok().body(mapper.map(resposta.get(), RespostaDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> update(Long id, RespostaFormDTO form) {
        Optional<Resposta> resposta = respostaRepository.findById(id);
        if (resposta.isPresent()) {
            Resposta entity = resposta.get();
            if (form.getIdAutor() != null && form.getIdTopico() != null) {
                Optional<Usuario> autor = usuarioRepository.findById(form.getIdAutor());
                Optional<Topico> topico = topicoRepository.findById(form.getIdTopico());
                if (autor.isPresent()) {
                    if (topico.isPresent()) {
                        entity.setTopico(topico.get());
                        entity.setAutor(autor.get());

                        List<Resposta> respostas = topico.get().getRespostas();
                        respostas.add(entity);
                        topico.get().setRespostas(respostas);
                        topicoRepository.save(topico.get());
                    } else return ((BodyBuilder) ResponseEntity.notFound()).body("Tópico não encontrado");
                } else return ((BodyBuilder) ResponseEntity.notFound()).body("Usuário autor não encontrado");
            }

            if (form.getMensagem() != null)
                entity.setMensagem(form.getMensagem());

            respostaRepository.save(entity);
            return ResponseEntity.ok().body(mapper.map(entity, RespostaDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<RespostaDTO> delete(Long id) {
        Optional<Resposta> resposta = respostaRepository.findById(id);
        if (resposta.isPresent()) {
            respostaRepository.delete(resposta.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> setSolucao(Long id) {
        Optional<Resposta> respostaOptional = respostaRepository.findById(id);
        if (respostaOptional.isPresent()) {
            Resposta entity = respostaOptional.get();
            entity.setSolucao(true);
            entity.getTopico().setStatus(StatusTopico.SOLUCIONADO);
            respostaRepository.save(entity);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
