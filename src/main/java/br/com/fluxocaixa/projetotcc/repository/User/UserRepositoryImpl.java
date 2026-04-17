package br.com.fluxocaixa.projetotcc.repository.User;
import br.com.fluxocaixa.projetotcc.dto.UserDto;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.Filter.UserFilter;
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

public class UserRepositoryImpl implements UserRepositoryQuery{
    @PersistenceContext
    private EntityManager manager;

    @Override
    public PageImpl<UserDto> filtrar(UserFilter filter, Pageable pageable) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<UserDto> criteria = builder.createQuery(UserDto.class);

        Root<User> root = criteria.from(User.class);

        criteria.select(builder.construct(UserDto.class,
                root.get("id"),
                root.get("user"),
                root.get("email")
        ));

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("user")));

        TypedQuery<UserDto> query = manager.createQuery(criteria);
        addRestPag(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private long total(UserFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<User> root = criteria.from(User.class);

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void addRestPag(TypedQuery<UserDto> query, Pageable pageable) {
        int pagAtual = pageable.getPageNumber();
        int totalRegPorPag = pageable.getPageSize();
        int primRegPag = pagAtual * totalRegPorPag;

        query.setFirstResult(primRegPag);
        query.setMaxResults(totalRegPorPag);
    }

    private Predicate[] criarRest(UserFilter filter, CriteriaBuilder builder, Root<User> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filter.getEmail())) {
            predicates.add(builder.like(root.get("email"), "%" + filter.getEmail() + "%"));
        }
        if (StringUtils.hasText(filter.getUser())) {
            predicates.add(builder.like(root.get("user"), "%" + filter.getUser() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
