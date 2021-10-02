package com.compasso.duvidas.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.compasso.duvidas.constants.StatusTopico;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "topicos")
@Data
public class Topico {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	
	private String descricao;
	@OneToMany(mappedBy = "topico")
	@JsonManagedReference
	private List<Resposta> respostas = new ArrayList<>();
	
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	@ManyToOne
	@JsonBackReference
	private Usuario autor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Curso curso;

	@Enumerated(EnumType.STRING)
	private StatusTopico status = StatusTopico.NAO_RESPONDIDO;

	public void close() {
		this.status = StatusTopico.FECHADO;
	}

	@Override
	//tive que alterar o toString pois estava criando uma recursão infinita
	public String toString() {
		return "Topico{" +
				"id=" + id +
				", titulo='" + titulo + '\'' +
				", descricao='" + descricao + '\'' +
				", respostas=" + respostas +
				", dataCriacao=" + dataCriacao +
				", autor=" + autor +
				", curso=" + curso.getId() +
				", status=" + status +
				'}';
	}
}
