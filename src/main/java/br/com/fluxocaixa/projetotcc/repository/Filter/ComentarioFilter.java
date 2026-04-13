package br.com.fluxocaixa.projetotcc.repository.Filter;

import lombok.Data;

import java.util.Date;

@Data
public class ComentarioFilter {
    private String conteudoTexto;
    private Date dataPublicacao;
    private Long upvotes;
}
