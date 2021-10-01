package com.compasso.duvidas.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.compasso.duvidas.constants.Categoria;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Singular;

@Entity
@Table(name = "cursos")
@Data

public class Curso {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;

	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="curso")
	@JsonManagedReference
	private List<Topico> topicos = new ArrayList<>();;


	public void adicionarTopico(Topico topico){
		this.getTopicos().add(topico);
	}
}
