package br.com.fluxocaixa.projetotcc.dto;

import jakarta.validation.constraints.NotBlank;

public record AlterarSenhaDto(
        @NotBlank String senhaAtual,
        @NotBlank String novaSenha
) {
}
