package br.com.fluxocaixa.projetotcc.repository.User;

import br.com.fluxocaixa.projetotcc.dto.UserDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryQuery {
    Page<UserDto> filtrar(UserFilter filter, Pageable pageable);
}
