package com.compasso.duvidas.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PerfilFormDTO {
	
	@NotEmpty(message = "nome is required")
	String nome;
	
}
