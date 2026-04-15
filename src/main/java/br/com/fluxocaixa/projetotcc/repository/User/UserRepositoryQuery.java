package br.com.fluxocaixa.projetotcc.repository.User;

import br.com.fluxocaixa.projetotcc.dto.UserDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.UserFilter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryQuery {
    public PageImpl<UserDto> filtrar(UserFilter userFilter, Pageable pageable);
}
