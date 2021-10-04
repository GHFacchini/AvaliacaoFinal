package com.compasso.duvidas.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class TopicoFormDTO {
	
	@NotEmpty(message = "titulo is required")
	private String titulo;
	
	@NotEmpty(message = "descricao is required")
	private String descricao;
	
//	@NotNull(message = "curso is required")
	private Long cursoId;

	private Long autorId;

}
