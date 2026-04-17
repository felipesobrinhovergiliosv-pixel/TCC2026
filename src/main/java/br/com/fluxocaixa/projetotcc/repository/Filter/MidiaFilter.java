package br.com.fluxocaixa.projetotcc.repository.Filter;


import br.com.fluxocaixa.projetotcc.model.TipoMidia;
import lombok.Data;

@Data
public class MidiaFilter {
    private String nome;
    private TipoMidia tipoMidia;

}
