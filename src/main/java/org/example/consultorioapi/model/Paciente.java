package org.example.consultorioapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "pacientes", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter @Setter @NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "email é obrigatório")
    @Email(message = "email inválido")
    @Column(nullable = false, unique = true)
    private String email;

    private String telefone;

    @Past(message = "data deve ser no passado")
    private LocalDate dataNascimento;

    @NotNull(message = "especialidade é obrigatória")
    @ManyToOne
    @JoinColumn(name = "especialidade_id", nullable = false)
    private Especialidade especialidade;
}