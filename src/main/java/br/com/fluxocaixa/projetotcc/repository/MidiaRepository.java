package br.com.fluxocaixa.projetotcc.repository;

import br.com.fluxocaixa.projetotcc.model.Midia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MidiaRepository extends JpaRepository<Midia, Long> {
}
