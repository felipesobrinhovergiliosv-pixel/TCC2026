package br.com.fluxocaixa.projetotcc.repository.Filter;

import lombok.Data;

import java.util.Date;

@Data
public class ProgressoUsuarioFilter {
    private Boolean concluido;
    private Date dataConclusao;
}
