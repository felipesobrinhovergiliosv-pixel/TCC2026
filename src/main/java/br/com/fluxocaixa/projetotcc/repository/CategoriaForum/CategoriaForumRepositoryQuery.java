package br.com.fluxocaixa.projetotcc.repository.CategoriaForum;

import br.com.fluxocaixa.projetotcc.dto.CategoriaForumDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.CategoriaForumFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaForumRepositoryQuery {
    public Page<CategoriaForumDto> filtrar(CategoriaForumFilter categoriaForumFilter, Pageable pageable);
}
