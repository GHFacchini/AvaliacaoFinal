package com.compasso.duvidas.entities;

import javax.persistence.*;

import com.compasso.duvidas.constants.TipoUsuario;

import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;

	@ManyToOne
	private Turma turma;
	
	private	String email;
	
	private String senha;
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;
	
}
