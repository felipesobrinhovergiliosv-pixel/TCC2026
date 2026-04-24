package br.com.fluxocaixa.projetotcc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user")
public class User {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String user;

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    @PositiveOrZero
    @Column(name = "xp_total")
    private Integer xpTotal;

    @DecimalMin("1")
    private Integer nivel;

    @Column(name = "data_criacao")
    private Date dataCriacao;
}

