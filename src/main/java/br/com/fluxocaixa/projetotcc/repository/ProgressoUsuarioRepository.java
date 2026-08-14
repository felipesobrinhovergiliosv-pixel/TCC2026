package br.com.fluxocaixa.projetotcc.repository;

import br.com.fluxocaixa.projetotcc.model.ProgressoUsuario;
import br.com.fluxocaixa.projetotcc.repository.ProgressoUsuario.ProgressoUsuarioRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressoUsuarioRepository extends JpaRepository<ProgressoUsuario, Long>, ProgressoUsuarioRepositoryQuery {
    List<ProgressoUsuario> findByUserId(Long userId);
    Optional<ProgressoUsuario> findByUserIdAndLicaoId(Long userId, Long licaoId);
}
