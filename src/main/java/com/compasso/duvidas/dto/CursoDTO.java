package com.compasso.duvidas.dto;

import java.util.ArrayList;
import java.util.List;

import com.compasso.duvidas.constants.Categoria;
import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.entities.Topico;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoDTO {
	
	private Long id;
	private String nome;
	private Categoria categoria;
	private List<String> topicosIds = new ArrayList<>();
	
	public CursoDTO(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
		this.categoria = curso.getCategoria();
		topicosInfo(curso);
	}

	private void topicosInfo(Curso curso){
		List<String> topicos = new ArrayList<>();
		for(Topico topico : curso.getTopicos()){
			topicos.add("Id: " + topico.getId() + " Título: "+ topico.getTitulo() +" Status: "+  topico.getStatus());
		}
		this.topicosIds = topicos;
	}
	
}
