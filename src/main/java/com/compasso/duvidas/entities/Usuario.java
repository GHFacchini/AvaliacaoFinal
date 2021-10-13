package com.compasso.duvidas.entities;

import javax.persistence.*;

import com.compasso.duvidas.constants.TipoUsuario;
import com.compasso.duvidas.dto.TurmaDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;

	@ManyToMany
	private List<Turma> turmas = new ArrayList<>();
	
	private	String email;
	
	private String senha;

	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;


	@Override
	public String toString() {
		return "Usuario{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", turmas=" + turmas +
				", email='" + email + '\'' +
				", tipoUsuario=" + tipoUsuario +
				'}';
	}
}
