package br.com.fluxocaixa.projetotcc.repository;

import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.User.UserRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
<<<<<<< HEAD
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
=======
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryQuery {
>>>>>>> cfe49b2164657282d6fbbc88a0201fbadd0e8f96
}
