package com.compasso.duvidas.services;

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
import org.springframework.web.multipart.MultipartFile;

import com.compasso.duvidas.constants.StatusTopico;
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
    @Transactional
    public ResponseEntity<?> save(RespostaFormDTO form) {

        Optional<Topico> topicoOptional = topicoRepository.findById(form.getIdTopico());
        //se não acha o tópcio retorna not found
        if (!topicoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        //se o tópico está fechado retorna bad request
        if (topicoOptional.get().getStatus() == StatusTopico.FECHADO) {
            return ResponseEntity.badRequest().body("O tópico está fechado");
        }

        Optional<Usuario> autorOptional = usuarioRepository.findById(form.getIdAutor());
        // se não achar o autor retorna not found
        if (!autorOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
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

        RespostaDTO respostaDTO = new RespostaDTO(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(respostaDTO);


    }


    @Override
    public Page<RespostaDTO> findAll(Pageable page) {
        Page<Resposta> respostas = respostaRepository.findAll(page);
        Page<RespostaDTO> respostasDTOS = respostas.map(RespostaDTO::new);
        return respostasDTOS;
    }

    @Override
    public ResponseEntity<RespostaDTO> findById(Long id) {
        Optional<Resposta> respostaOptional = respostaRepository.findById(id);
        if (respostaOptional.isPresent()) {
            return ResponseEntity.ok().body(new RespostaDTO(respostaOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
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
            return ResponseEntity.ok().body(new RespostaDTO(entity));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public ResponseEntity<RespostaDTO> delete(Long id) {
        Optional<Resposta> respostaOptional = respostaRepository.findById(id);
        if (!respostaOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if(contaRespostas(respostaOptional.get().getTopico()) == 1){
            respostaOptional.get().getTopico().setStatus(StatusTopico.NAO_RESPONDIDO);
        }else{
            if (respostaOptional.get().getSolucao() && contaSolucoes(respostaOptional.get().getTopico()) == 1) {
                respostaOptional.get().getTopico().setStatus(StatusTopico.NAO_SOLUCIONADO);
            }
        }

        respostaRepository.delete(respostaOptional.get());
        topicoRepository.save(respostaOptional.get().getTopico());
        return ResponseEntity.ok().build();


    }


    //inverte valor de solucao (true/false)
    //utiliza o metodo contaSolucos para que se só houver uma solucao ao trocar o valor dela o topico
    //volte a ser não solucionado
    @Override
    @Transactional
    public ResponseEntity<?> setSolucao(Long id) {
        Optional<Resposta> respostaOptional = respostaRepository.findById(id);
        if (!respostaOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Resposta entity = respostaOptional.get();
        //se essa resposta for a solucao
        if (entity.getSolucao()) {
            //e for a unica solucao
            if (contaSolucoes(entity.getTopico()) == 1) {
                //topico volta a ser não solucionado
                entity.getTopico().setStatus(StatusTopico.NAO_SOLUCIONADO);
            }
        } else {   //se a resposta não era solucao agora o topico estará solucinado
            entity.getTopico().setStatus(StatusTopico.SOLUCIONADO);
        }
        //inverte o valor (true/false)
        entity.setSolucao(!entity.getSolucao());

        respostaRepository.save(entity);

        topicoRepository.save(entity.getTopico());

        return ResponseEntity.ok().build();
    }
    
	@Override
	@Transactional
	public boolean bindArquivoResposta(Long id, MultipartFile arquivo) {
		Optional<Resposta> respostaOptional = respostaRepository.findById(id);
		if(!respostaOptional.isPresent()) return true;
		
		Resposta resposta = respostaOptional.get();
		
		resposta.setArquivo(arquivo.getOriginalFilename());
		respostaRepository.save(resposta);
		
		return false;
	}

    //conta quantas solucoes existem
    private int contaSolucoes(Topico topico) {
        int cont = 0;
        for (Resposta resposta : topico.getRespostas()) {
            if (resposta.getSolucao()) {
                cont++;
            }
        }
        return cont;
    }

    private int contaRespostas(Topico topico) {
        int cont = 0;
        for (Resposta resposta : topico.getRespostas()){
            cont++;
        }
        return cont;
    }

}
