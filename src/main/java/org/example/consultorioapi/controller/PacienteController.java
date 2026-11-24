package org.example.consultorioapi.controller;

import org.example.consultorioapi.model.Paciente;
import jakarta.validation.Valid;
import org.example.consultorioapi.repository.EspecialidadeRepository;
import org.example.consultorioapi.repository.PacienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteRepository repository;
    private final EspecialidadeRepository especialidadeRepository;

    public PacienteController(PacienteRepository repository, EspecialidadeRepository especialidadeRepository) {
        this.repository = repository;
        this.especialidadeRepository = especialidadeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar(@RequestParam(required = false) String nome) {
        if (nome == null || nome.isBlank()) {
            return ResponseEntity.ok(repository.findAll());
        }
        return ResponseEntity.ok(repository.findByNomeContainingIgnoreCase(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paciente> criar(@Valid @RequestBody Paciente paciente) {
        if (repository.existsByEmail(paciente.getEmail())) {
            return ResponseEntity.status(409).build();
        }
        if (!especialidadeRepository.existsById(paciente.getEspecialidade().getId())) {
            return ResponseEntity.badRequest().build();
        }
        Paciente salvo = repository.save(paciente);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @Valid @RequestBody Paciente dados) {
        return repository.findById(id).map(paciente -> {
            if (!paciente.getEmail().equals(dados.getEmail())
                    && repository.existsByEmail(dados.getEmail())) {
                return ResponseEntity.status(409).<Paciente>build();
            }
            if (!especialidadeRepository.existsById(dados.getEspecialidade().getId())) {
                return ResponseEntity.badRequest().<Paciente>build();
            }
            paciente.setNome(dados.getNome());
            paciente.setEmail(dados.getEmail());
            paciente.setTelefone(dados.getTelefone());
            paciente.setDataNascimento(dados.getDataNascimento());
            paciente.setEspecialidade(dados.getEspecialidade());
            return ResponseEntity.ok(repository.save(paciente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}