package br.com.fluxocaixa.projetotcc.repository.Midia;
import br.com.fluxocaixa.projetotcc.dto.MidiaDto;
import br.com.fluxocaixa.projetotcc.model.Midia;
import br.com.fluxocaixa.projetotcc.model.TipoMidia;
import br.com.fluxocaixa.projetotcc.repository.Filter.MidiaFilter;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MidiaRepositoryImpl implements MidiaRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public PageImpl<MidiaDto> filtrar(MidiaFilter filter, Pageable pageable) {

        CriteriaBuilder builder= manager.getCriteriaBuilder();

        CriteriaQuery<MidiaDto> criteria = builder.createQuery(MidiaDto.class);

        Root<Midia> root = criteria.from(Midia.class);

        criteria.select(builder.construct(MidiaDto.class,
                root.get("id"),
                root.get("nome"),
                root.get("tipomidia")
        ));

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("nome")));

        TypedQuery<MidiaDto> query = manager.createQuery(criteria);
        addRestPag(query,pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));

    }

    private long total(MidiaFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Midia> root = criteria.from(Midia.class);

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void addRestPag(TypedQuery<MidiaDto> query, Pageable pageable) {
        int pagAtual = pageable.getPageNumber();
        int totalRegPorPag = pageable.getPageSize();
        int primRegPag = pagAtual * totalRegPorPag;

        query.setFirstResult(primRegPag);
        query.setMaxResults(totalRegPorPag);
    }

    private Predicate[] criarRest(MidiaFilter filter, CriteriaBuilder builder, Root<Midia> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filter.getNome())) {
            predicates.add(builder.like(root.get("nome"), "%" + filter.getNome() + "%"));
        }
        if (StringUtils.hasText(String.valueOf(filter.getTipoMidia()))) {
            predicates.add(builder.like(root.get("tipoMidia").get("id"), "%" + filter.getNome() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
