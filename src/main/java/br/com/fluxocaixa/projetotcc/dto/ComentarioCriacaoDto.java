package br.com.fluxocaixa.projetotcc.dto;

import br.com.fluxocaixa.projetotcc.model.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Usado só na criação (POST /comentario). user, upvotes e dataPublicacao são definidos
// pelo servidor no ComentarioController, então não podem estar no corpo validado pelo @Valid.
public record ComentarioCriacaoDto(
        @NotNull Post post,
        @NotBlank String conteudoTexto
) {
}
