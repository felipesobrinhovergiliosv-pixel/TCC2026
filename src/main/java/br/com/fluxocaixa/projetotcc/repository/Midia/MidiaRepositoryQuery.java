package br.com.fluxocaixa.projetotcc.repository.Midia;

import br.com.fluxocaixa.projetotcc.dto.MidiaDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.MidiaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface MidiaRepositoryQuery {
    Page<MidiaDto> filtrar(MidiaFilter filter, Pageable pageable);
}
