package br.com.fluxocaixa.projetotcc.dto;

import jakarta.validation.constraints.NotBlank;

// Usado só no cadastro (POST /user). Diferente de receber a entidade User inteira, não deixa
// o cliente mandar "plano", "admin" etc. no corpo da requisição.
public record UserCadastroDto(
        @NotBlank String user,
        @NotBlank String email,
        @NotBlank String senha
) {
}
