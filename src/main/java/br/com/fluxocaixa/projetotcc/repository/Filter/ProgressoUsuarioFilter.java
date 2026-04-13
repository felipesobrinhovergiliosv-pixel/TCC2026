package br.com.fluxocaixa.projetotcc.repository.Filter;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class ProgressoUsuarioFilter {
    private Boolean concluido;

    @Column(name = "data_conclusao")
    private Date dataConclusao;
}
