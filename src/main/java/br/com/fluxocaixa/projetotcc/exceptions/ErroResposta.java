package br.com.fluxocaixa.projetotcc.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErroResposta {
    private String mensagem;
    private int status;
}
