package br.com.fluxocaixa.projetotcc.dto;

import br.com.fluxocaixa.projetotcc.model.CategoriaForum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Usado só na criação (POST /post). user, upvotes e data_publicacao são definidos pelo
// servidor no PostController, então não podem estar no corpo validado pelo @Valid.
public record PostCriacaoDto(
        @NotBlank String titulo,
        @NotBlank String conteudo_texto,
        @NotNull CategoriaForum categoriaForum
) {
}
