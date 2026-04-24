package br.com.fluxocaixa.projetotcc.Controller;
import br.com.fluxocaixa.projetotcc.dto.MidiaDto;
import br.com.fluxocaixa.projetotcc.model.Midia;
import br.com.fluxocaixa.projetotcc.repository.Filter.MidiaFilter;
import br.com.fluxocaixa.projetotcc.repository.Midia.MidiaRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.MidiaRepository;
import br.com.fluxocaixa.projetotcc.service.MidiaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/midia")
public class MidiaController {

    @Autowired
    private MidiaRepository repository;

    @Autowired
    private MidiaService service;

    @GetMapping
    public List<Midia> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<MidiaDto> listarPorNome(MidiaFilter midiaFilter, Pageable pageable){
        return repository.filtrar(midiaFilter, pageable);
    }

    @GetMapping("/{midiaId}")
    public Midia buscar(@PathVariable Long midiaId ){
        return service.buscaroufalhar(midiaId);
    }

    @PostMapping
    public Midia adicionar(@RequestBody Midia midia) { return service.salvar(midia); }

    @DeleteMapping("/{midiaId}")
    public void remover(@PathVariable Long midiaId){ service.excluir(midiaId);}

    @PutMapping("/{midiaId}")
    public Midia alterar(@PathVariable Long midiaId, @RequestBody Midia midia){
        Midia midiaAtual = service.buscaroufalhar(midiaId);

        BeanUtils.copyProperties(midia, midiaAtual, "id");
        return service.salvar(midiaAtual);
    }

}

