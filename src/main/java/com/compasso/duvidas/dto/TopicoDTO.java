package com.compasso.duvidas.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.compasso.duvidas.constants.StatusTopico;
import com.compasso.duvidas.entities.Curso;
import com.compasso.duvidas.entities.Resposta;
import com.compasso.duvidas.entities.Topico;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TopicoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private List<String> respostas = new ArrayList<>();
    private LocalDateTime dataCriacao;
    private String autor;
    private String curso;
    private StatusTopico status;

    public TopicoDTO(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.descricao = topico.getDescricao();
        respostasInfo(topico);
        this.dataCriacao = topico.getDataCriacao();
        cursoInfo(topico);
        autorInfo(topico);
        this.status = topico.getStatus();
    }

    private void cursoInfo(Topico topico) {
        this.curso = "Id: " + topico.getCurso().getId() + " | Título: " + topico.getCurso().getNome() +
                " | Categoria: " + topico.getCurso().getCategoria();
    }

    private void autorInfo(Topico topico) {
        this.autor = "Id: " + topico.getAutor().getId() + " | Nome: " + topico.getAutor().getNome() +
                " | " + topico.getAutor().getPerfis().get(0).getAuthority();
    }

    private void respostasInfo(Topico topico) {
        List<String> respostas = new ArrayList<>();
        for (Resposta resposta : topico.getRespostas()) {
            if (resposta.getSolucao()) {
                respostas.add("Id: " + resposta.getId() + " **SOLUÇÃO**");
            } else {
                respostas.add("Id: " + resposta.getId());
            }
        }
        this.respostas = respostas;

    }
}
