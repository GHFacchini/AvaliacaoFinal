package com.compasso.duvidas.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.compasso.duvidas.constants.StatusTopico;
import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.entities.Resposta;
import com.compasso.duvidas.entities.Topico;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TopicoDTO {
	
	private Long id;
	private String titulo;
	private String descricao;
	@JsonIgnore
	private List<Resposta> respostas;
	private LocalDateTime dataCriacao;
	private String autor;
	private Curso curso;
	private StatusTopico status;
	
	public TopicoDTO(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.descricao = topico.getDescricao();
		this.respostas = topico.getRespostas();
		this.dataCriacao = topico.getDataCriacao();
//		this.autor = topico.getAutor().getNome();
		this.curso = topico.getCurso();
		this.status = topico.getStatus();
	}
}
