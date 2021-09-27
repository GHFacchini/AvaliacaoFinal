package com.compasso.duvidas.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.compasso.duvidas.constants.StatusTopico;

import lombok.Data;

@Entity
@Table(name = "topicos")
@Data
public class Topico {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	
	private String descricao;
	
	private List<Resposta> respostas;
	
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	@ManyToOne
	private Usuario autor;
	
	@ManyToOne
	private Curso curso;
	
	private StatusTopico status = StatusTopico.NAO_RESPONDIDO;
	
}
