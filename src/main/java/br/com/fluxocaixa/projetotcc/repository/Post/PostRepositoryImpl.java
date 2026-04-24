package br.com.fluxocaixa.projetotcc.repository.Post;
import br.com.fluxocaixa.projetotcc.dto.PostDto;
import br.com.fluxocaixa.projetotcc.model.Post;
import br.com.fluxocaixa.projetotcc.repository.Filter.PostFilter;
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

public class PostRepositoryImpl implements PostRepositoryQuery{
    @PersistenceContext
    private EntityManager manager;

    @Override
    public PageImpl<PostDto> filtrar(PostFilter filter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<PostDto> criteria = builder.createQuery(PostDto.class);

        Root<Post> root = criteria.from(Post.class);

        criteria.select(builder.construct(PostDto.class,
                root.get("titulo"),
                root.get("data_publicacao"),
                root.get("upvotes")
        ));

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("titulo")));

        TypedQuery<PostDto> query = manager.createQuery(criteria);
        addRestPag(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private long total(PostFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Post> root = criteria.from(Post.class);

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void addRestPag(TypedQuery<PostDto> query, Pageable pageable) {
        int pagAtual = pageable.getPageNumber();
        int totalRegPorPag = pageable.getPageSize();
        int primRegPag = pagAtual * totalRegPorPag;

        query.setFirstResult(primRegPag);
        query.setMaxResults(totalRegPorPag);
    }

    private Predicate[] criarRest(PostFilter filter, CriteriaBuilder builder, Root<Post> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filter.getTitulo())) {
            predicates.add(builder.like(root.get("titulo"), "%" + filter.getTitulo() + "%"));
        }
        if (filter.getData_publicacao() != null) {
            predicates.add(builder.equal(root.get("data_publicacao"), filter.getData_publicacao()));
        }
        if (filter.getUpvotes() != null) {
            predicates.add(builder.equal(root.get("Upvotes"), filter.getUpvotes()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
