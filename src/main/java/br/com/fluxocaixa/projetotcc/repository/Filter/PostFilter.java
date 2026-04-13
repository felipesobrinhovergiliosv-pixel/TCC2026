package br.com.fluxocaixa.projetotcc.repository.Filter;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class PostFilter {
    private String titulo;
    private Long upvotes;

    @Column(name = "data_publicacao")
    private Date dataPublicacao;
}
