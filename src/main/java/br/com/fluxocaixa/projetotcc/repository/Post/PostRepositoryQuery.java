package br.com.fluxocaixa.projetotcc.repository.Post;

import br.com.fluxocaixa.projetotcc.dto.PostDto;
import br.com.fluxocaixa.projetotcc.repository.Filter.PostFilter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryQuery {
    public PageImpl<PostDto> filtrar(PostFilter postFilter, Pageable pageable);
}
