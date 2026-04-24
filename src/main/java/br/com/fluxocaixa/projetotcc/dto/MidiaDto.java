package br.com.fluxocaixa.projetotcc.dto;

import br.com.fluxocaixa.projetotcc.model.TipoMidia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MidiaDto {
    private String nome;
    private TipoMidia tipoMidia;
}
