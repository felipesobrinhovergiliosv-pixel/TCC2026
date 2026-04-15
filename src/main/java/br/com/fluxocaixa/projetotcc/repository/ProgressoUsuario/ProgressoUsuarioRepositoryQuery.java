package br.com.fluxocaixa.projetotcc.repository.ProgressoUsuario;

import br.com.fluxocaixa.projetotcc.dto.ProgressoUsuarioDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.ProgressoUsuarioFilter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ProgressoUsuarioRepositoryQuery {
    public PageImpl<ProgressoUsuarioDto> filtrar(ProgressoUsuarioFilter progressoUsuarioFilter, Pageable pageable);
}
