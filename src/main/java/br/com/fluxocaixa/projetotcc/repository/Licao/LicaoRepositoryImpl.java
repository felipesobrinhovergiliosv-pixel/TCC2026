package br.com.fluxocaixa.projetotcc.repository.Licao;
import br.com.fluxocaixa.projetotcc.dto.LicaoDto;
import br.com.fluxocaixa.projetotcc.model.Licao;
import br.com.fluxocaixa.projetotcc.repository.Filter.LicaoFilter;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class LicaoRepositoryImpl implements LicaoRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public PageImpl<LicaoDto> filtrar(LicaoFilter filter, Pageable pageable) {
        CriteriaBuilder builder= manager.getCriteriaBuilder();

        CriteriaQuery<LicaoDto> criteria = builder.createQuery(LicaoDto.class);

        Root<Licao> root = criteria.from(Licao.class);

        criteria.select(builder.construct(LicaoDto.class,
                root.get("titulo"),
                root.get("conteudo"),
                root.get("modulo")
        ));

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("titulo")));

        TypedQuery<LicaoDto> query = manager.createQuery(criteria);
        addRestPag(query,pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private long total(LicaoFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Licao> root = criteria.from(Licao.class);

        Predicate[] predicates = criarRest(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

    private void addRestPag(TypedQuery<LicaoDto> query, Pageable pageable) {
        int pagAtual = pageable.getPageNumber();
        int totalRegPorPag = pageable.getPageSize();
        int primRegPag = pagAtual * totalRegPorPag;

        query.setFirstResult(primRegPag);
        query.setMaxResults(totalRegPorPag);
    }

    private Predicate[] criarRest(LicaoFilter filter, CriteriaBuilder builder, Root<Licao> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filter.getTitulo())){
            predicates.add(builder.like(root.get("titulo"), "%" + filter.getTitulo() + "%"));
        }
        if (filter.getConteudo() != null) {
            predicates.add(builder.equal(root.get("conteudo"), filter.getConteudo()));
        }
        if (filter.getModulo() != null) {
            predicates.add(builder.equal(root.get("modulo").get("id"), filter.getModulo()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
