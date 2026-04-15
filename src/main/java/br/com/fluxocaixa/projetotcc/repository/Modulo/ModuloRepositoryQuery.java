package br.com.fluxocaixa.projetotcc.repository.Modulo;

import br.com.fluxocaixa.projetotcc.dto.ModuloDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.ModuloFilter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ModuloRepositoryQuery {
    public PageImpl<ModuloDto> filtrar(ModuloFilter filter, Pageable pageable);
}
