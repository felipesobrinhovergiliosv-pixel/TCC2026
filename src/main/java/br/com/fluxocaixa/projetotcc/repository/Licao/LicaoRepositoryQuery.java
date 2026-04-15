package br.com.fluxocaixa.projetotcc.repository.Licao;

import br.com.fluxocaixa.projetotcc.dto.LicaoDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.LicaoFilter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface LicaoRepositoryQuery {
    public PageImpl<LicaoDto> filtrar(LicaoFilter filter, Pageable pageable);
}
