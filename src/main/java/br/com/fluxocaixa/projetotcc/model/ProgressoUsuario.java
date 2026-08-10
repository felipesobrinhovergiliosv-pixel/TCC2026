package br.com.fluxocaixa.projetotcc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "progresso_usuario")
public class ProgressoUsuario {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Boolean concluido;

    @NotNull
    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"email", "plano", "dataCriacao", "admin"})
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "licao_id")
    private Licao licao;
}
