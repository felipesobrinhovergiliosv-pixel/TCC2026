package br.com.fluxocaixa.projetotcc.repository;

import br.com.fluxocaixa.projetotcc.model.CategoriaForum;
import br.com.fluxocaixa.projetotcc.repository.CategoriaForum.CategoriaForumRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaForumRepository extends JpaRepository<CategoriaForum, Long>, CategoriaForumRepositoryQuery {
}
