package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.PostCriacaoDto;
import br.com.fluxocaixa.projetotcc.dto.PostCurtidaResultadoDto;
import br.com.fluxocaixa.projetotcc.dto.PostDto;
import br.com.fluxocaixa.projetotcc.model.Post;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.Filter.PostFilter;
import br.com.fluxocaixa.projetotcc.repository.Post.PostRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.PostRepository;
import br.com.fluxocaixa.projetotcc.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostRepository repository;
    @Autowired
    private PostService service;

    @GetMapping
    public List<Post> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<PostDto> listarPorNome(PostFilter postFilter, Pageable pageable){
        return repository.filtrar(postFilter, pageable);
    }

    @GetMapping("/curtidos")
    public List<Long> curtidos(@AuthenticationPrincipal User usuarioLogado){
        return service.postsCurtidosPeloUsuario(usuarioLogado);
    }

    @GetMapping("/{postId}")
    public Post buscar(@PathVariable Long postId ){
        return service.buscaroufalhar(postId);
    }

    @PostMapping
    public Post adicionar(@RequestBody @Valid PostCriacaoDto dados, @AuthenticationPrincipal User usuarioLogado) {
        Post post = new Post();
        post.setTitulo(dados.titulo());
        post.setConteudo_texto(dados.conteudo_texto());
        post.setCategoriaForum(dados.categoriaForum());
        post.setUser(usuarioLogado);
        post.setUpvotes(0);
        post.setData_publicacao(LocalDate.now());
        return service.salvar(post);
    }

    @PostMapping("/{postId}/curtir")
    public PostCurtidaResultadoDto curtir(@PathVariable Long postId, @AuthenticationPrincipal User usuarioLogado){
        return service.curtir(postId, usuarioLogado);
    }

    @DeleteMapping("/{postId}")
    public void remover(@PathVariable Long postId, @AuthenticationPrincipal User usuarioLogado){
        Post post = service.buscaroufalhar(postId);
        service.validarDono(post, usuarioLogado);
        service.excluir(postId);
    }

    @PutMapping("/{postId}")
    public Post alterar(@PathVariable Long postId, @RequestBody @Valid Post post, @AuthenticationPrincipal User usuarioLogado){
        Post postAtual = service.buscaroufalhar(postId);
        service.validarDono(postAtual, usuarioLogado);

        BeanUtils.copyProperties(post, postAtual, "id", "user", "upvotes", "data_publicacao");
        return service.salvar(postAtual);
    }

}
