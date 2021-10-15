package com.compasso.duvidas.dto;

import com.compasso.duvidas.entities.Perfil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PerfilDTO {
	
	private Long id;
	private String nome;
	
	public PerfilDTO(Perfil perfil) {
		this.id = perfil.getId();
		this.nome = perfil.getNome();
	}
	
}
