package com.compasso.duvidas.repositories;

import com.compasso.duvidas.entities.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
