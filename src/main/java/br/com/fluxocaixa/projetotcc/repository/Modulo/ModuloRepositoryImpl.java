package br.com.fluxocaixa.projetotcc.repository.Modulo;

import br.com.fluxocaixa.projetotcc.dto.ModuloDto;
import br.com.fluxocaixa.projetotcc.model.Modulo;
import br.com.fluxocaixa.projetotcc.repository.Filter.ModuloFilter;
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

public class ModuloRepositoryImpl implements ModuloRepositoryQuery{
    @PersistenceContext
    private EntityManager manager;

    @Override
    public PageImpl<ModuloDto> filtrar(ModuloFilter filter, Pageable pageable) {

        CriteriaBuilder builder= manager.getCriteriaBuilder();

        CriteriaQuery<ModuloDto> criteria = builder.createQuery(ModuloDto.class);

        Root<Modulo> root = criteria.from(Modulo.class);

        criteria.select(builder.construct(ModuloDto.class,
                root.get("id"),
                root.get("titulo")
        ));

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("titulo")));

        TypedQuery<ModuloDto> query = manager.createQuery(criteria);
        addRestPag(query,pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));

    }

    private long total(ModuloFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Modulo> root = criteria.from(Modulo.class);

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void addRestPag(TypedQuery<ModuloDto> query, Pageable pageable) {
        int pagAtual = pageable.getPageNumber();
        int totalRegPorPag = pageable.getPageSize();
        int primRegPag = pagAtual * totalRegPorPag;

        query.setFirstResult(primRegPag);
        query.setMaxResults(totalRegPorPag);
    }

    private Predicate[] criarRest(ModuloFilter filter, CriteriaBuilder builder, Root<Modulo> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filter.getTitulo())) {
            predicates.add(builder.like(root.get("titulo"), "%" + filter.getTitulo() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
