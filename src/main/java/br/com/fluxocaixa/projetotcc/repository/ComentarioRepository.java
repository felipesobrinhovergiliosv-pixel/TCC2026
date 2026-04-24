package br.com.fluxocaixa.projetotcc.repository;

import br.com.fluxocaixa.projetotcc.model.Comentario;
import br.com.fluxocaixa.projetotcc.repository.CategoriaForum.CategoriaForumRepositoryQuery;
import br.com.fluxocaixa.projetotcc.repository.Comentario.ComentarioRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>, ComentarioRepositoryQuery {
}
