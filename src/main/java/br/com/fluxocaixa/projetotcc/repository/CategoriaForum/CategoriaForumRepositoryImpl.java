package br.com.fluxocaixa.projetotcc.repository.CategoriaForum;

import br.com.fluxocaixa.projetotcc.dto.CategoriaForumDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.CategoriaForumFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CategoriaForumRepositoryImpl implements CategoriaForumRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public PageImpl<CategoriaForumDto> filtrar(CategoriaForumFilter filter, Pageable pageable) {
        return null;
    }
}
