package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.ModuloDto;
import br.com.fluxocaixa.projetotcc.model.Modulo;
import br.com.fluxocaixa.projetotcc.repository.Filter.ModuloFilter;
import br.com.fluxocaixa.projetotcc.repository.Modulo.ModuloRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.ModuloRepository;
import br.com.fluxocaixa.projetotcc.service.ModuloService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moduloController")
public class ModuloController {

    @Autowired
    private ModuloRepository repository;

    @Autowired
    private ModuloRepositoryImpl repositoryimpl;

    @Autowired
    private ModuloService service;

    @GetMapping
    public List<Modulo> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<ModuloDto> listarPorNome(ModuloFilter moduloFilter, Pageable pageable){
        return repositoryimpl.filtrar(moduloFilter, pageable);
    }

    @GetMapping("/{moduloId}")
    public Modulo buscar(@PathVariable Long moduloId ){
        return service.buscaroufalhar(moduloId);
    }

    @PostMapping
    public Modulo adicionar(@RequestBody Modulo modulo) { return service.salvar(modulo); }

    @DeleteMapping("/{moduloId}")
    public void remover(@PathVariable Long moduloId){ service.excluir(moduloId);}

    @PutMapping("/{moduloId}")
    public Modulo alterar(@PathVariable Long moduloId, @RequestBody Modulo modulo){
        Modulo moduloAtual = service.buscaroufalhar(moduloId);

        BeanUtils.copyProperties(modulo, moduloAtual, "id");
        return service.salvar(moduloAtual);
    }

}
