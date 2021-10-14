package com.compasso.duvidas.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.compasso.duvidas.constants.Categoria;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "cursos")
@Data

public class Curso {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;

	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	@OneToMany(cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<Topico> topicos = new ArrayList<>();


	@Override
	public String toString() {
		List<Long> topicosIds = new ArrayList<>();
		for(Topico topico : this.getTopicos()){
			topicosIds.add(topico.getId());
		}

		return "Curso{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", categoria=" + categoria +
				", topicos=" + topicosIds +
				'}';
	}
}
