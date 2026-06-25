package br.com.fluxocaixa.projetotcc.repository;

import br.com.fluxocaixa.projetotcc.model.Game;
import br.com.fluxocaixa.projetotcc.repository.Game.GameRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>, GameRepositoryQuery {
}
