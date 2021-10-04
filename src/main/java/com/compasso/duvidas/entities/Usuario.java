package com.compasso.duvidas.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.compasso.duvidas.constants.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;

	@ManyToOne
	@JsonBackReference
	private Turma turma;
	
	private	String email;
	
	private String senha;
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;


	@Override
	public String toString() {
		return "Usuario{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", turma=" + turma +
				", email='" + email + '\'' +
				", tipoUsuario=" + tipoUsuario +
				'}';
	}
}
