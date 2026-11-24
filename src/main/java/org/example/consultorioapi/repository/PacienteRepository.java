package org.example.consultorioapi.repository;

import org.example.consultorioapi.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByEmail(String email);
    List<Paciente> findByNomeContainingIgnoreCase(String nome);
}