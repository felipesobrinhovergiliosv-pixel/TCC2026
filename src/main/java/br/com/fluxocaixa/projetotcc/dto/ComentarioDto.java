package br.com.fluxocaixa.projetotcc.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDto {
    @Column(name = "conteudo_texto")
    private String conteudoTexto;

    @Column(name = "data_publicacao")
    private Date dataPublicacao;

    private Long upvotes;
}
