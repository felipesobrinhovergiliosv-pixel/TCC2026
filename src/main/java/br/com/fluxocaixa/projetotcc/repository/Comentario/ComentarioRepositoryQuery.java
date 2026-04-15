package br.com.fluxocaixa.projetotcc.repository.Comentario;

import br.com.fluxocaixa.projetotcc.dto.ComentarioDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.ComentarioFilter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ComentarioRepositoryQuery {
    public PageImpl<ComentarioDto> filtar(ComentarioFilter comentarioFilter, Pageable pageable);
}
