package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.LicaoDto;
import br.com.fluxocaixa.projetotcc.model.Licao;
import br.com.fluxocaixa.projetotcc.repository.Filter.LicaoFilter;
import br.com.fluxocaixa.projetotcc.repository.Licao.LicaoRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.LicaoRepository;
import br.com.fluxocaixa.projetotcc.service.LicaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/licao")
public class LicaoController {
    @Autowired
    private LicaoRepository repository;

    @Autowired
    private LicaoService service;

    @GetMapping
    public List<Licao> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<LicaoDto> listarPorNome(LicaoFilter licaoFilter, Pageable pageable){
        return repository.filtrar(licaoFilter, pageable);
    }

    @GetMapping("/{licaoId}")
    public Licao buscar(@PathVariable Long licaoId ){
        return service.buscaroufalhar(licaoId);
    }

    @PostMapping
    public Licao adicionar(@RequestBody Licao licao) { return service.salvar(licao); }

    @DeleteMapping("/{licaoId}")
    public void remover(@PathVariable Long licaoId){ service.excluir(licaoId);}

    @PutMapping("/{licaoId}")
    public Licao alterar(@PathVariable Long licaoId, @RequestBody Licao licao){
        Licao licaoAtual = service.buscaroufalhar(licaoId);

        BeanUtils.copyProperties(licao, licaoAtual, "id");
        return service.salvar(licaoAtual);
    }

}
