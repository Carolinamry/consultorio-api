package org.example.consultorioapi.repository;

import org.example.consultorioapi.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    boolean existsByNome(String nome);
}