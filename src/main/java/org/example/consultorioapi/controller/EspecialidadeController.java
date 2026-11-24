package org.example.consultorioapi.controller;

import org.example.consultorioapi.model.Especialidade;
import jakarta.validation.Valid;
import org.example.consultorioapi.repository.EspecialidadeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

    private final EspecialidadeRepository repository;

    public EspecialidadeController(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Especialidade> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Especialidade> criar(@Valid @RequestBody Especialidade e) {
        if (repository.existsByNome(e.getNome())) {
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.status(201).body(repository.save(e));
    }
}