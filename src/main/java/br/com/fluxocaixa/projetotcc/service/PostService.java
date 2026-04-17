package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.Post;
import br.com.fluxocaixa.projetotcc.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Post salvar(Post post){ return postRepository.save(post); }

    public Post buscaroufalhar(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado com esse Id"));
    }

    @Transactional
    public void excluir(Long postId){ postRepository.deleteById(postId); }
}
