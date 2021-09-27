package com.compasso.duvidas.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.compasso.duvidas.entities.Curso;

import lombok.Data;

@Data
public class TopicoFormDTO {
	
	@NotEmpty(message = "titulo is required")
	private String titulo;
	
	@NotEmpty(message = "descricao is required")
	private String descricao;
	
//	@NotNull(message = "curso is required")
	private Curso curso;

}
