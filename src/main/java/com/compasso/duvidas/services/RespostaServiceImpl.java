package com.compasso.duvidas.services;

import java.util.List;
import java.util.Optional;

import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.repositories.CursoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    @Transactional
    public ResponseEntity<?> save(Long cursoId, Long topicoId, RespostaFormDTO form) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        Optional<Topico> topicoOptional = topicoRepository.findById(topicoId);
        Optional<Usuario> autorOptional = usuarioRepository.findById(form.getIdAutor());
        ResponseEntity<?> response = verificaOptionalToSave(cursoOptional, topicoOptional, autorOptional);
        if (response != null) {
            return response;
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
    public ResponseEntity<?> findById(Long cursoId, Long topicoId, Long respostaId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        Optional<Topico> topicoOptional = topicoRepository.findById(topicoId);
        Optional<Resposta> respostaOptional = respostaRepository.findById(respostaId);
        ResponseEntity<?> response = verificaOptional(cursoOptional, topicoOptional, respostaOptional);
        if (response != null) {
            return response;
        }

        return ResponseEntity.ok().body(new RespostaDTO(respostaOptional.get()));
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Long cursoId, Long topicoId, Long respostaId, RespostaFormDTO form) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        Optional<Topico> topicoOptional = topicoRepository.findById(topicoId);
        Optional<Resposta> respostaOptional = respostaRepository.findById(respostaId);
        Optional<Usuario> autorOptional = usuarioRepository.findById(form.getIdAutor());
        ResponseEntity<?> response = verificaOptional(cursoOptional, topicoOptional, respostaOptional, autorOptional);
        if (response != null) {
            return response;
        }

        Resposta entity = respostaOptional.get();
        if(form.getMensagem() != null){
            entity.setMensagem(form.getMensagem());
        }

        respostaRepository.save(entity);
        return ResponseEntity.ok().body(new RespostaDTO(entity));

    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long cursoId, Long topicoId, Long respostaId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        Optional<Topico> topicoOptional = topicoRepository.findById(topicoId);
        Optional<Resposta> respostaOptional = respostaRepository.findById(respostaId);
        ResponseEntity<?> response = verificaOptional(cursoOptional, topicoOptional, respostaOptional);
        if (response != null) {
            return response;
        }

        //atualiza o status do tópico se necessários
        Topico topico = updateTopicoAfterDelete(respostaOptional.get());

        respostaRepository.delete(respostaOptional.get());
        topicoRepository.save(topico);
        return ResponseEntity.ok().body("A resposta de id: " + respostaId + " foi deletada");
    }


    //inverte valor de solucao (true/false)
    @Override
    @Transactional
    public ResponseEntity<?> setSolucao(Long cursoId, Long topicoId, Long respostaId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        Optional<Topico> topicoOptional = topicoRepository.findById(topicoId);
        Optional<Resposta> respostaOptional = respostaRepository.findById(respostaId);
        ResponseEntity<?> response = verificaOptional(cursoOptional, topicoOptional, respostaOptional);
        if (response != null) {
            return response;
        }

        Resposta resposta = updateTopicoAfterSolucao(respostaOptional.get());

        respostaRepository.save(resposta);

        topicoRepository.save(resposta.getTopico());

        return ResponseEntity.ok().body(new RespostaDTO(resposta));
    }

    @Override
    @Transactional
    public boolean bindArquivoResposta(Long id, MultipartFile arquivo) {
        Optional<Resposta> respostaOptional = respostaRepository.findById(id);
        if (!respostaOptional.isPresent()) return true;

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

    //conta quantas resposta existem
    private int contaRespostas(Topico topico) {
        int cont = 0;
        for (Resposta resposta : topico.getRespostas()) {
            cont++;
        }
        return cont;
    }




    private ResponseEntity<?> verificaOptionalToSave(Optional<Curso> curso, Optional<Topico> topico, Optional<Usuario> autor) {

        if (!curso.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }
        if ((!topico.isPresent() || !curso.get().getTopicos().contains(topico.get()))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topico não encontrado");
        }
        if (topico.get().getStatus() == StatusTopico.FECHADO) {
            return ResponseEntity.badRequest().body("O tópico está fechado");
        }
        if (!autor.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return null;
    }

    //verifica a presença dos optionals e se as entidades mães possum as filhas (curso>topico>resposta)
    private ResponseEntity<?> verificaOptional(Optional<Curso> curso, Optional<Topico> topico, Optional<Resposta> resposta) {
        if (!curso.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }
        if ((!topico.isPresent() || !curso.get().getTopicos().contains(topico.get()))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topico não encontrado");
        }
        if ((!resposta.isPresent() || !topico.get().getRespostas().contains(resposta.get()))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resposta não encontrada");
        }
        return null;
    }

    private ResponseEntity<?> verificaOptional(Optional<Curso> curso, Optional<Topico> topico, Optional<Resposta> resposta, Optional<Usuario> autor) {
        if (!curso.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");
        }
        if ((!topico.isPresent() || !curso.get().getTopicos().contains(topico.get()))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topico não encontrado");
        }
        if ((!resposta.isPresent() || !topico.get().getRespostas().contains(resposta.get()))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resposta não encontrada");
        }
        if (!autor.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return null;
    }




    //atualiza o status do tópico se necessários
    private Topico updateTopicoAfterDelete(Resposta resposta) {
        Topico topico = resposta.getTopico();
        if (contaRespostas(topico) == 1) {
            topico.setStatus(StatusTopico.NAO_RESPONDIDO);
        } else {
            if (resposta.getSolucao() && contaSolucoes(topico) == 1) {
                topico.setStatus(StatusTopico.NAO_SOLUCIONADO);
            }
        }
        return topico;
    }

    //atualiza o status do tópico se necessário
    private Resposta updateTopicoAfterSolucao(Resposta resposta) {
        //se essa resposta for a solucao
        if (resposta.getSolucao()) {
            //e for a unica solucao
            if (contaSolucoes(resposta.getTopico()) == 1) {
                //topico volta a ser não solucionado
                resposta.getTopico().setStatus(StatusTopico.NAO_SOLUCIONADO);
            }
        } else {   //se a resposta não era solucao agora o topico estará solucinado
            resposta.getTopico().setStatus(StatusTopico.SOLUCIONADO);
        }
        //inverte o valor (true/false)
        resposta.setSolucao(!resposta.getSolucao());
        return resposta;
    }

}
