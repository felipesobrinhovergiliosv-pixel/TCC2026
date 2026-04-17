package br.com.fluxocaixa.projetotcc.repository.Filter;

import br.com.fluxocaixa.projetotcc.model.Modulo;
import lombok.Data;

@Data
public class LicaoFilter {
    private String titulo;

    private String conteudo;

    private Modulo modulo;
}
