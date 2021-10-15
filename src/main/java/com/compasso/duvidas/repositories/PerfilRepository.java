package com.compasso.duvidas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.compasso.duvidas.entities.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{

	Optional<Perfil> findByNome(String string);

}
