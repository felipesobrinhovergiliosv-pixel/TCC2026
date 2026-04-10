package br.com.fluxocaixa.projetotcc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "progressousuario")
public class ProressoUsuario {

    @NotNull
    private Boolean concluido;

    @NotNull
    private LocalDate data_conclusão;

    //userid
    //licaoid
}
