package br.com.fluxocaixa.projetotcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "post")
public class Post {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank  // nao pode ser nulo (string)
    private String titulo;

    @NotBlank  // nao pode ser nulo (string)
    private String conteudo_texto;

    @NotBlank  // nao pode ser nulo (string)
    private String midia_url;

    @PositiveOrZero //preco não pode ser negativo
    @NotNull // nao pode ser nulo
    //likes no post
    private Integer upvotes;

    @NotNull
    private LocalDate data_publicação;

    //categoriaid
    //userid
    //midiaid
}
