package br.com.fluxocaixa.projetotcc.repository;

import br.com.fluxocaixa.projetotcc.model.Licao;
import br.com.fluxocaixa.projetotcc.repository.Licao.LicaoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicaoRepository extends JpaRepository<Licao, Long>, LicaoRepositoryQuery {
}
