package br.com.fluxocaixa.projetotcc.repository.ProgressoUsuario;

import br.com.fluxocaixa.projetotcc.dto.ProgressoUsuarioDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.ProgressoUsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ProgressoUsuarioRepositoryQuery {
    Page<ProgressoUsuarioDto> filtrar(ProgressoUsuarioFilter filter, Pageable pageable);
}
