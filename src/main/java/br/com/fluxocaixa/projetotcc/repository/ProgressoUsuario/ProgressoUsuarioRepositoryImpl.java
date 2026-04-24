package br.com.fluxocaixa.projetotcc.repository.ProgressoUsuario;
import br.com.fluxocaixa.projetotcc.dto.ProgressoUsuarioDto;
import br.com.fluxocaixa.projetotcc.model.ProgressoUsuario;
import br.com.fluxocaixa.projetotcc.repository.Filter.ProgressoUsuarioFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ProgressoUsuarioRepositoryImpl implements ProgressoUsuarioRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public PageImpl<ProgressoUsuarioDto> filtrar(ProgressoUsuarioFilter filter, Pageable pageable) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<ProgressoUsuarioDto> criteria = builder.createQuery(ProgressoUsuarioDto.class);

        Root<ProgressoUsuario> root = criteria.from(ProgressoUsuario.class);

        criteria.select(builder.construct(ProgressoUsuarioDto.class,
                root.get("concluido"),
                root.get("dataConclusao")
        ));

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("concluido")));

        TypedQuery<ProgressoUsuarioDto> query = manager.createQuery(criteria);
        addRestPag(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));

    }

    private long total(ProgressoUsuarioFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<ProgressoUsuario> root = criteria.from(ProgressoUsuario.class);

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void addRestPag(TypedQuery<ProgressoUsuarioDto> query, Pageable pageable) {
        int pagAtual = pageable.getPageNumber();
        int totalRegPorPag = pageable.getPageSize();
        int primRegPag = pagAtual * totalRegPorPag;

        query.setFirstResult(primRegPag);
        query.setMaxResults(totalRegPorPag);
    }

    private Predicate[] criarRest(ProgressoUsuarioFilter filter, CriteriaBuilder builder, Root<ProgressoUsuario> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getConcluido() != null) {
            predicates.add(builder.equal(root.get("concluido"), filter.getConcluido()));
        }
        if (filter.getDataConclusao() != null) {
            predicates.add(builder.equal(root.get("dataConclusao"), filter.getDataConclusao()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
