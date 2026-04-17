package br.com.fluxocaixa.projetotcc.repository.Midia;

import br.com.fluxocaixa.projetotcc.dto.MidiaDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.MidiaFilter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface MidiaRepositoryQuery {
    public PageImpl<MidiaDto> filtrar(MidiaFilter filter, Pageable pageable);
}
