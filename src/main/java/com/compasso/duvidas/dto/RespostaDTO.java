package com.compasso.duvidas.dto;

import java.time.LocalDateTime;

import com.compasso.duvidas.entities.Resposta;
import com.compasso.duvidas.entities.Topico;
import com.compasso.duvidas.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RespostaDTO {
	
	private Long id;
	private Usuario autor;
	private Topico topico;
	private String mensagem;
	private LocalDateTime dataCriacao;
	private Boolean solucao;
	
	public RespostaDTO(Resposta resposta) {
		this.id = resposta.getId();
		this.autor = resposta.getAutor();
		this.topico = resposta.getTopico();
		this.mensagem = resposta.getMensagem();
		this.dataCriacao = resposta.getDataCriacao();
		this.solucao = resposta.getSolucao();
	}
	
}
