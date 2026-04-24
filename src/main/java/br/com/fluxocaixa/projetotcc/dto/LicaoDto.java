package br.com.fluxocaixa.projetotcc.dto;

import br.com.fluxocaixa.projetotcc.model.Modulo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicaoDto {
    private String titulo;

    private String conteudo;

    private Modulo modulo;
}
