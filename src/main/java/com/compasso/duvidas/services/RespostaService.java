package com.compasso.duvidas.services;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.compasso.duvidas.dto.RespostaDTO;
import com.compasso.duvidas.dto.RespostaFormDTO;

public interface RespostaService {

	// no recurso /cursos/topicos
	ResponseEntity<?> save(Long cursoId, Long topicoId, @Valid RespostaFormDTO form);
	ResponseEntity<?> findById(Long cursoId, Long topicoId, Long respostaId);
	ResponseEntity<?> update(Long cursoId, Long topicoId, Long respostaId, RespostaFormDTO form);
	ResponseEntity<?> delete(Long cursoId, Long topicoId, Long respostaId);
	ResponseEntity<?> setSolucao(Long cursoId, Long topicoId, Long respostaId);
	boolean bindArquivoResposta(Long id, MultipartFile arquivo);

	//fora do recurso /cursos/topicos
	Page<RespostaDTO> findAll(Pageable page);

}
