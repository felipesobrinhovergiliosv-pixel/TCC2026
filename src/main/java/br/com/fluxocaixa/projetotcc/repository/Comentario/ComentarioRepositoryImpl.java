package br.com.fluxocaixa.projetotcc.repository.Comentario;

import br.com.fluxocaixa.projetotcc.dto.ComentarioDto;
import br.com.fluxocaixa.projetotcc.model.Comentario;
import br.com.fluxocaixa.projetotcc.repository.Filter.ComentarioFilter;
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

public class ComentarioRepositoryImpl implements ComentarioRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public PageImpl<ComentarioDto> filtrar(ComentarioFilter filter, Pageable pageable) {

        CriteriaBuilder builder= manager.getCriteriaBuilder();

        CriteriaQuery<ComentarioDto> criteria = builder.createQuery(ComentarioDto.class);

        Root<Comentario> root = criteria.from(Comentario.class);

        criteria.select(builder.construct(ComentarioDto.class,
                root.get("conteudoTexto"),
                root.get("dataPublicação"),
                root.get("upvotes")
        ));

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("conteudoTexto")));

        TypedQuery<ComentarioDto> query = manager.createQuery(criteria);
        addRestPag(query,pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private long total(ComentarioFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Comentario> root = criteria.from(Comentario.class);

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void addRestPag(TypedQuery<ComentarioDto> query, Pageable pageable) {
        int pagAtual = pageable.getPageNumber();
        int totalRegPorPag = pageable.getPageSize();
        int primRegPag = pagAtual * totalRegPorPag;

        query.setFirstResult(primRegPag);
        query.setMaxResults(totalRegPorPag);
    }

    private Predicate[] criarRest(ComentarioFilter filter, CriteriaBuilder builder, Root<Comentario> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filter.getConteudoTexto())) {
            predicates.add(builder.like(root.get("conteudo_texto"), "%" + filter.getConteudoTexto() + "%"));
        }
        if (filter.getDataPublicacao() != null) {
            predicates.add(builder.equal(root.get("dataPublicacao"), filter.getDataPublicacao()));
        }
        if (filter.getUpvotes() != null) {
            predicates.add(builder.equal(root.get("dataUpvotes"), filter.getUpvotes()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
