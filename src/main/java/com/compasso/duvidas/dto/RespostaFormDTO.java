package com.compasso.duvidas.dto;

import lombok.Data;

@Data
public class RespostaFormDTO {
	
	private Long idAutor;
	private Long idTopico;
	private String mensagem;
	private Boolean solucao;

}
