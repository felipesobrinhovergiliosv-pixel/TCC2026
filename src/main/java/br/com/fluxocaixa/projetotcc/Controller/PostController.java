package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.PostDto;
import br.com.fluxocaixa.projetotcc.model.Post;
import br.com.fluxocaixa.projetotcc.repository.Filter.PostFilter;
import br.com.fluxocaixa.projetotcc.repository.Post.PostRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.PostRepository;
import br.com.fluxocaixa.projetotcc.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{postId}")
    public Post buscar(@PathVariable Long postId ){
        return service.buscaroufalhar(postId);
    }

    @PostMapping
    public Post adicionar(@RequestBody Post post) { return service.salvar(post); }

    @DeleteMapping("/{postId}")
    public void remover(@PathVariable Long postId){ service.excluir(postId);}

    @PutMapping("/{postId}")
    public Post alterar(@PathVariable Long postId, @RequestBody Post post){
        Post postAtual = service.buscaroufalhar(postId);

        BeanUtils.copyProperties(post, postAtual, "id");
        return service.salvar(postAtual);
    }

}
