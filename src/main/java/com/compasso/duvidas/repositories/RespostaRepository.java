package com.compasso.duvidas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.compasso.duvidas.entities.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long>{
    Page<Resposta> findByTopico_Id(Pageable page, Long id);

}
