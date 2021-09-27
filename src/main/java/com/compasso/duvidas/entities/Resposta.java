package com.compasso.duvidas.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "resposta")
@Data
public class Resposta {
	
	private Long id;
	
	@ManyToOne
	private Usuario autor;
	
	@ManyToOne
	private Topico topico;
	
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	private Boolean solucao = false;
}
