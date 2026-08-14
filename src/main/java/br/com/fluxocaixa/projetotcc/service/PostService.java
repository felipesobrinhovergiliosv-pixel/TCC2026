package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.dto.PostCurtidaResultadoDto;
import br.com.fluxocaixa.projetotcc.model.Post;
import br.com.fluxocaixa.projetotcc.model.PostCurtida;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.PostCurtidaRepository;
import br.com.fluxocaixa.projetotcc.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCurtidaRepository postCurtidaRepository;

    @Transactional
    public Post salvar(Post post){ return postRepository.save(post); }

    public Post buscaroufalhar(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado com esse Id"));
    }

    @Transactional
    public void excluir(Long postId){ postRepository.deleteById(postId); }

    // Alterna curtir/descurtir: se o usuário já curtiu, remove o registro e devolve o placar;
    // senão, cria o registro. A constraint única em post_curtida impede duplicar mesmo sob concorrência.
    @Transactional
    public PostCurtidaResultadoDto curtir(Long postId, User usuarioLogado){
        Post post = buscaroufalhar(postId);
        var existente = postCurtidaRepository.findByPostIdAndUserId(postId, usuarioLogado.getId());

        boolean curtido;
        if (existente.isPresent()) {
            postCurtidaRepository.delete(existente.get());
            post.setUpvotes(post.getUpvotes() - 1);
            curtido = false;
        } else {
            PostCurtida curtida = new PostCurtida();
            curtida.setPost(post);
            curtida.setUser(usuarioLogado);
            postCurtidaRepository.save(curtida);
            post.setUpvotes(post.getUpvotes() + 1);
            curtido = true;
        }

        Post salvo = postRepository.save(post);
        return new PostCurtidaResultadoDto(salvo.getUpvotes(), curtido);
    }

    public List<Long> postsCurtidosPeloUsuario(User usuarioLogado){
        return postCurtidaRepository.findByUserId(usuarioLogado.getId())
                .stream()
                .map(curtida -> curtida.getPost().getId())
                .toList();
    }

    // Só o autor do post (ou um admin) pode editar/apagar.
    public void validarDono(Post post, User usuarioLogado) {
        boolean ehDono = post.getUser().getId().equals(usuarioLogado.getId());
        boolean ehAdmin = Boolean.TRUE.equals(usuarioLogado.getAdmin());

        if (!ehDono && !ehAdmin) {
            throw new AccessDeniedException("Você não tem permissão para alterar este post.");
        }
    }
}
