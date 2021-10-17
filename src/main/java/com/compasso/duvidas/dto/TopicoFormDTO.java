package com.compasso.duvidas.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TopicoFormDTO {
	
	@NotEmpty(message = "titulo is required")
	private String titulo;
	
	@NotEmpty(message = "descricao is required")
	private String descricao;

	@NotNull(message = "autorId is required")
	private Long autorId;

}
