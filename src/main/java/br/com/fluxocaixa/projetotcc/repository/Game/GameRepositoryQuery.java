package br.com.fluxocaixa.projetotcc.repository.Game;

import br.com.fluxocaixa.projetotcc.dto.GameDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.GameFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameRepositoryQuery {
    Page<GameDto> filtrar(GameFilter filter, Pageable pageable);
}
