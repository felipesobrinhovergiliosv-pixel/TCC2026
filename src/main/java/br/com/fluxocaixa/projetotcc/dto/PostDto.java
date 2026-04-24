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
public class PostDto {
    private String titulo;

    private Long upvotes;

    @Column(name = "data_publicacao")
    private Date data_publicacao;
}
