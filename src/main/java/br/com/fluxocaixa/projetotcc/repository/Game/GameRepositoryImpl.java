package br.com.fluxocaixa.projetotcc.repository.Game;

import br.com.fluxocaixa.projetotcc.dto.GameDto;
import br.com.fluxocaixa.projetotcc.model.Game;
import br.com.fluxocaixa.projetotcc.repository.Filter.GameFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class GameRepositoryImpl implements GameRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<GameDto> filtrar(GameFilter filter, Pageable pageable) {
        CriteriaBuilder builder= manager.getCriteriaBuilder();

        CriteriaQuery<GameDto> criteria = builder.createQuery(GameDto.class);

        Root<Game> root = criteria.from(Game.class);

        criteria.select(builder.construct(GameDto.class,
                root.get("vidas"),
                root.get("moedas"),
                root.get("streak")
        ));

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("id")));

        TypedQuery<GameDto> query = manager.createQuery(criteria);
        addRestPag(query,pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private long total(GameFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Game> root = criteria.from(Game.class);

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void addRestPag(TypedQuery<GameDto> query, Pageable pageable) {
        int pagAtual = pageable.getPageNumber();
        int totalRegPorPag = pageable.getPageSize();
        int primRegPag = pagAtual * totalRegPorPag;

        query.setFirstResult(primRegPag);
        query.setMaxResults(totalRegPorPag);
    }

    private Predicate[] criarRest(GameFilter filter, CriteriaBuilder builder, Root<Game> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getVidas() != null) {
            predicates.add(builder.equal(root.get("vidas"), filter.getVidas()));
        }
        if (filter.getMoedas() != null) {
            predicates.add(builder.equal(root.get("moedas"), filter.getMoedas()));
        }
        if (filter.getStreak() != null) {
            predicates.add(builder.equal(root.get("streak"), filter.getStreak()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
