package br.com.fluxocaixa.projetotcc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "comentario")
public class Comentario {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"email", "plano", "dataCriacao", "admin"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comentario comentario;

    @NotBlank
    @Column(name = "conteudo_texto")
    private String conteudoTexto;

    // Comentário de texto simples não é obrigado a ter mídia (ver V0003__Comentario_Midia_Opcional.sql)
    @ManyToOne
    @JoinColumn(name = "midia_id")
    private Midia midia;


    @PositiveOrZero
    private Long upvotes;

    @Column(name = "data_publicacao")
    private Date dataPublicacao;

}


