package com.compasso.duvidas.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "turmas")
@Data
public class Turma {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@ManyToMany
	private List<Usuario> usuarios = new ArrayList<>();

	@OneToMany
	@JsonManagedReference
	private List<Sprint> sprints = new ArrayList<>();



	public void adicionarUsuario(Usuario usuario){this.getUsuarios().add(usuario);}

	@Override
	public String toString() {
		List<Long> usuariosIds = new ArrayList<>();
		for(Usuario usuario : this.usuarios){
			usuariosIds.add(usuario.getId());
		}
		return "Turma{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", usuarios=" + usuariosIds +
				", sprints=" + sprints +
				'}';
	}
}
