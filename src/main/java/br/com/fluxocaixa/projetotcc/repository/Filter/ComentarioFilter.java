package br.com.fluxocaixa.projetotcc.repository.Filter;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class ComentarioFilter {

    @Column(name = "conteudo_texto")
    private String conteudoTexto;

    @Column(name = "data_publicacao")
    private Date dataPublicacao;
    private Long upvotes;
}
