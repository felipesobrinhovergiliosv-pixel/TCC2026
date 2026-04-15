package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.CategoriaForumDto;
import br.com.fluxocaixa.projetotcc.model.CategoriaForum;
import br.com.fluxocaixa.projetotcc.repository.CategoriaForum.CategoriaForumRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.CategoriaForumRepository;
import br.com.fluxocaixa.projetotcc.repository.Filter.CategoriaForumFilter;
import br.com.fluxocaixa.projetotcc.service.CategoriaForumService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoriaForum")
public class CategoriaForumController {

    @Autowired
    private CategoriaForumRepository repository;

    @Autowired
    private CategoriaForumRepositoryImpl repositoryimpl;

    @Autowired
    private CategoriaForumService service;

    @GetMapping
    public List<CategoriaForum> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<CategoriaForumDto> listarPorNome(CategoriaForumFilter categoriaForumFilter, Pageable pageable){
        return repositoryimpl.filtrar(categoriaForumFilter, pageable);
    }

    @GetMapping("/{categoriaForumId}")
    public CategoriaForum buscar(@PathVariable Long categoriaForumId ){
        return service.buscaroufalhar(categoriaForumId);
    }

    @PostMapping
    public CategoriaForum adicionar(@RequestBody CategoriaForum categoriaForum) { return service.salvar(categoriaForum); }

    @DeleteMapping("/{categoriaForumId}")
    public void remover(@PathVariable Long categoriaForumId){ service.excluir(categoriaForumId);}

    @PutMapping("/{categoriaForumId}")
    public CategoriaForum alterar(@PathVariable Long categoriaForumId, @RequestBody CategoriaForum categoriaForum){
        CategoriaForum categoriaForumAtual = service.buscaroufalhar(categoriaForumId);

        BeanUtils.copyProperties(categoriaForum, categoriaForumAtual, "id");
        return service.salvar(categoriaForumAtual);
    }

}
