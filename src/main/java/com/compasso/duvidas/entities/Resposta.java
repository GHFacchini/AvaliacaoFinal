package com.compasso.duvidas.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "respostas")
@Data
public class Resposta {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JsonBackReference
	private Usuario autor;
	
	@ManyToOne
	@JsonBackReference
	private Topico topico;
	
	private String mensagem;
	
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	private Boolean solucao = false;
}
