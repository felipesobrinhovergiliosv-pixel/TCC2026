package br.com.fluxocaixa.projetotcc.repository;

import br.com.fluxocaixa.projetotcc.model.PostCurtida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostCurtidaRepository extends JpaRepository<PostCurtida, Long> {
    Optional<PostCurtida> findByPostIdAndUserId(Long postId, Long userId);

    List<PostCurtida> findByUserId(Long userId);
}
