package com.compasso.duvidas.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.compasso.duvidas.constants.TipoUsuario;

import lombok.Data;
import lombok.Getter;

@Data
public class UsuarioFormDTO {
    @NotEmpty(message = "nome is required")
    private String nome;

    private List<Long> turmasIds;

    @NotEmpty(message = "email is required")
    private	String email;

	@NotEmpty(message = "senha is required")
    @Getter 
    private String senha;
    
    private Long perfilId;
    
    public void setSenha(String senha) {
		this.senha = new BCryptPasswordEncoder().encode(senha);
	}
}
