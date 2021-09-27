package com.compasso.duvidas.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.compasso.duvidas.constants.TipoUsuario;

import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private Turma turma;
	
	private	String email;
	
	private String senha;
	
	private TipoUsuario tipoUsuario;
	
}
