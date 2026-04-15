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
    private CategoriaForumRepository categoriaForumRepository;

    @Autowired
    private CategoriaForumRepositoryImpl categoriaForumRepositoryimpl;

    @Autowired
    private CategoriaForumService categoriaForumService;

    @GetMapping
    public List<CategoriaForum> listar() {
        return categoriaForumRepository.findAll();
    }

    @GetMapping("/pornome")
    public Page<CategoriaForumDto> listarPorNome(CategoriaForumFilter categoriaForumFilter, Pageable pageable){
        return categoriaForumRepositoryimpl.filtrar(categoriaForumFilter, pageable);
    }

    @GetMapping("/{categoriaForumId}")
    public CategoriaForum buscar(@PathVariable Long categoriaForumId ){
        return categoriaForumService.buscaroufalhar(categoriaForumId);
    }

    @PostMapping
    public CategoriaForum adicionar(@RequestBody CategoriaForum categoriaForum) { return categoriaForumService.salvar(categoriaForum); }

    @DeleteMapping("/{categoriaForumId}")
    public void remover(@PathVariable Long categoriaForumId){ categoriaForumService.excluir(categoriaForumId);}

    @PutMapping("/{categoriaForumId}")
    public CategoriaForum alterar(@PathVariable Long categoriaForumId, @RequestBody CategoriaForum categoriaForum){
        CategoriaForum categoriaForumAtual = categoriaForumService.buscaroufalhar(categoriaForumId);

        BeanUtils.copyProperties(categoriaForum, categoriaForumAtual, "id");
        return categoriaForumService.salvar(categoriaForumAtual);
    }

}
