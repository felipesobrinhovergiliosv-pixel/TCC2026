package br.com.fluxocaixa.projetotcc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "game")
public class Game {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"email", "plano", "dataCriacao", "admin"})
    private User user;

    @NotNull
    @PositiveOrZero
    private Integer vidas;

    @NotNull
    @PositiveOrZero
    private Integer moedas;

    @NotNull
    @PositiveOrZero
    private Integer streak;
}
