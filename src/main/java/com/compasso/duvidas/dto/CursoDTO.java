package com.compasso.duvidas.dto;

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
	private List<Topico> topicos;
	
	public CursoDTO(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
		this.categoria = curso.getCategoria();
		this.topicos = curso.getTopicos();
	}
	
}
