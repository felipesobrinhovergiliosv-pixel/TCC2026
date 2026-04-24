package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.ComentarioDto;
import br.com.fluxocaixa.projetotcc.model.Comentario;

import br.com.fluxocaixa.projetotcc.repository.Comentario.ComentarioRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.ComentarioRepository;
import br.com.fluxocaixa.projetotcc.repository.Filter.ComentarioFilter;
import br.com.fluxocaixa.projetotcc.service.ComentarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    public Comentario adicionar(@RequestBody Comentario comentario) { return service.salvar(comentario); }

    @DeleteMapping("/{comentarioId}")
    public void remover(@PathVariable Long comentarioId){ service.excluir(comentarioId);}

    @PutMapping("/{comentarioId}")
    public Comentario alterar(@PathVariable Long comentarioId, @RequestBody Comentario comentario){
        Comentario comentarioAtual = service.buscaroufalhar(comentarioId);

        BeanUtils.copyProperties(comentario, comentarioAtual, "id");
        return service.salvar(comentarioAtual);
    }

}

