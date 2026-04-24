package br.com.fluxocaixa.projetotcc.repository.CategoriaForum;

import br.com.fluxocaixa.projetotcc.dto.CategoriaForumDto;
import br.com.fluxocaixa.projetotcc.model.CategoriaForum;
import br.com.fluxocaixa.projetotcc.repository.Filter.CategoriaForumFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoriaForumRepositoryImpl implements CategoriaForumRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public PageImpl<CategoriaForumDto> filtrar(CategoriaForumFilter filter, Pageable pageable) {

        CriteriaBuilder builder= manager.getCriteriaBuilder();

        CriteriaQuery<CategoriaForumDto> criteria = builder.createQuery(CategoriaForumDto.class);

        Root<CategoriaForum> root = criteria.from(CategoriaForum.class);

        criteria.select(builder.construct(CategoriaForumDto.class,
                root.get("nome")
        ));

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("nome")));

        TypedQuery<CategoriaForumDto> query = manager.createQuery(criteria);
        addRestPag(query,pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private long total(CategoriaForumFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<CategoriaForum> root = criteria.from(CategoriaForum.class);

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void addRestPag(TypedQuery<CategoriaForumDto> query, Pageable pageable) {
        int pagAtual = pageable.getPageNumber();
        int totalRegPorPag = pageable.getPageSize();
        int primRegPag = pagAtual * totalRegPorPag;

        query.setFirstResult(primRegPag);
        query.setMaxResults(totalRegPorPag);
    }

    private Predicate[] criarRest(CategoriaForumFilter filter, CriteriaBuilder builder, Root<CategoriaForum> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filter.getNome())){
            predicates.add(builder.like(root.get("nomeCliente"), "%" + filter.getNome() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
