package br.com.fluxocaixa.projetotcc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "midia")
public class Midia {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String midia_url;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_midia")
    private TipoMidia tipoMidia;
}
