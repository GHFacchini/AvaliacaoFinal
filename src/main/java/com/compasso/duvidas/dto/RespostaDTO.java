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
    private String autor;
    private String topico;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean solucao;
    private String arquivo;

    public RespostaDTO(Resposta resposta) {
        this.id = resposta.getId();
        autorInfo(resposta);
        topicoInfo(resposta);
        this.mensagem = resposta.getMensagem();
        this.dataCriacao = resposta.getDataCriacao();
        this.solucao = resposta.getSolucao();
        this.arquivo = resposta.getArquivo();
    }

    private void autorInfo(Resposta resposta) {
        this.autor = "Id: " + resposta.getAutor().getId() + " | nome: " + resposta.getAutor().getNome() +
                " | " + resposta.getAutor().getPerfis().get(0).getAuthority();
    }

    private void topicoInfo(Resposta resposta){
        this.topico = "Id: " + resposta.getTopico().getId() + " | Título: " + resposta.getTopico().getTitulo();
    }
}
