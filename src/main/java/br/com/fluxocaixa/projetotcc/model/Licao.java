package br.com.fluxocaixa.projetotcc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "licao")
public class Licao {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @Column(name = "conteudo_json")
    private String conteudo;

    @NotNull
    private Integer xp_recompensa;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;
}
