package com.compasso.duvidas.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.compasso.duvidas.constants.Categoria;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoFormDTO {
	
	@NotEmpty(message = "nome is required")
	private String nome;
	
	@NotNull(message = "categoria is required")
	@Getter private Categoria categoria;
	
	
	public void setCategoria(String categoria) {
		this.categoria = Categoria.valueOf(categoria.toUpperCase());
	}
	
}
