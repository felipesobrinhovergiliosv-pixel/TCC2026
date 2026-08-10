package br.com.fluxocaixa.projetotcc.dto;

import jakarta.validation.constraints.NotBlank;

public record LicaoRespostaDto(@NotBlank String resposta) {
}
