package com.compasso.duvidas.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.compasso.duvidas.entities.Perfil;
import com.compasso.duvidas.entities.Usuario;
import com.compasso.duvidas.repositories.PerfilRepository;
import com.compasso.duvidas.repositories.UsuarioRepository;

@Service
@Profile("prod")
public class _DBService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	    
    @Autowired
    private PerfilRepository perfilRepository;
	
  	public void instantiateProdDatabase() throws ParseException, IOException {
  		//Instanciando os dois perfis base
  		Perfil bolsista = new Perfil("ROLE_BOLSISTA");
		Perfil moderador = new Perfil("ROLE_MODERADOR");
		perfilRepository.saveAll(Arrays.asList(bolsista, moderador));
		
		//Instanciando um usuário moderador padrão
  		List<Perfil> perfil = new ArrayList<>();
  		perfil.add(perfilRepository.getOne((long) 2));
  		Usuario userModerador = new Usuario("MODERADOR", "moderador@compasso.com", new BCryptPasswordEncoder().encode("compasso2021"), perfil);
  		usuarioRepository.save(userModerador);
  	}
}
