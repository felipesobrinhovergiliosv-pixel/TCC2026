package br.com.fluxocaixa.projetotcc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.aspectj.apache.bcel.generic.LineNumberGen;

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
    @PositiveOrZero
    private Integer vidas;

    @NotNull
    @PositiveOrZero
    private Integer moedas;

    @NotNull
    @PositiveOrZero
    private Integer streak;
}
