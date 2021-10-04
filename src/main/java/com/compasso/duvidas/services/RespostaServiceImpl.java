package com.compasso.duvidas.services;

import java.util.Optional;

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
public class RespostaServiceImpl implements RespostaService{
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private RespostaRepository respostaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Override
	public ResponseEntity<RespostaDTO> save(RespostaFormDTO form) {
		Resposta entity = new Resposta();
		Optional<Usuario> autor = usuarioRepository.findById(form.getIdAutor());
		Optional<Topico> topico = topicoRepository.findById(form.getIdTopico());
		
		if(autor.isEmpty() || topico.isEmpty()) return ResponseEntity.notFound().build();
		entity.setAutor(autor.get());
		entity.setTopico(topico.get());
		entity.setSolucao(form.getSolucao());
		entity.setMensagem(form.getMensagem());
		
		topico.get().setAutor(autor.get());
		
		respostaRepository.save(entity);
		topicoRepository.save(topico.get());
		
		RespostaDTO respostaDTO = mapper.map(entity, RespostaDTO.class);
		
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
		Optional<Resposta> resposta = respostaRepository.findById(id);
		if (resposta.isPresent()) {
			return ResponseEntity.ok().body(mapper.map(resposta.get(), RespostaDTO.class));
		} else
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
				if(autor.isPresent()) {
					if(topico.isPresent()) {
						entity.setTopico(topico.get());
						entity.setAutor(autor.get());
						
						topico.get().setAutor(autor.get());
						topicoRepository.save(topico.get());
					} else return ((BodyBuilder) ResponseEntity.notFound()).body("Tópico não encontrado"); 
				} else return ((BodyBuilder) ResponseEntity.notFound()).body("Usuário autor não encontrado"); 
			}
			
			if(form.getMensagem() != null)
				entity.setMensagem(form.getMensagem());
			
			if(form.getSolucao() != null)
				entity.setSolucao(form.getSolucao());
			
			respostaRepository.save(entity);
			return ResponseEntity.ok().body(mapper.map(entity, RespostaDTO.class));
		} else
			return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<RespostaDTO> delete(Long id) {
		Optional<Resposta> resposta = respostaRepository.findById(id);
		if (resposta.isPresent()) {
			respostaRepository.delete(resposta.get());
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();
	}

}
