package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.ComentarioDto;
import br.com.fluxocaixa.projetotcc.model.Comentario;

import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.Comentario.ComentarioRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.ComentarioRepository;
import br.com.fluxocaixa.projetotcc.repository.Filter.ComentarioFilter;
import br.com.fluxocaixa.projetotcc.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioRepository repository;

    @Autowired
    private ComentarioService service;

    @GetMapping
    public List<Comentario> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<ComentarioDto> listarPorNome(ComentarioFilter comentarioFilter, Pageable pageable){
        return repository.filtrar(comentarioFilter, pageable);
    }

    @GetMapping("/{comentarioId}")
    public Comentario buscar(@PathVariable Long comentarioId ){
        return service.buscaroufalhar(comentarioId);
    }

    @PostMapping
    public Comentario adicionar(@RequestBody @Valid Comentario comentario, @AuthenticationPrincipal User usuarioLogado) {
        comentario.setUser(usuarioLogado);
        comentario.setUpvotes(0L);
        comentario.setDataPublicacao(new Date());
        return service.salvar(comentario);
    }

    @DeleteMapping("/{comentarioId}")
    public void remover(@PathVariable Long comentarioId, @AuthenticationPrincipal User usuarioLogado){
        Comentario comentario = service.buscaroufalhar(comentarioId);
        service.validarDono(comentario, usuarioLogado);
        service.excluir(comentarioId);
    }

    @PutMapping("/{comentarioId}")
    public Comentario alterar(@PathVariable Long comentarioId, @RequestBody @Valid Comentario comentario, @AuthenticationPrincipal User usuarioLogado){
        Comentario comentarioAtual = service.buscaroufalhar(comentarioId);
        service.validarDono(comentarioAtual, usuarioLogado);

        BeanUtils.copyProperties(comentario, comentarioAtual, "id", "user", "upvotes", "dataPublicacao");
        return service.salvar(comentarioAtual);
    }

}
