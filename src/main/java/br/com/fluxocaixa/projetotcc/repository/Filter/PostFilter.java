package br.com.fluxocaixa.projetotcc.repository.Filter;

import lombok.Data;

import java.util.Date;

@Data
public class PostFilter {
    private String titulo;
    private Long upvotes;
    private Date dataPublicacao;
}
