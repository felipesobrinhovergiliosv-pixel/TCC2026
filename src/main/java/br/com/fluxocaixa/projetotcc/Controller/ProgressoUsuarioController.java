package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.ProgressoUsuarioDto;
import br.com.fluxocaixa.projetotcc.model.ProgressoUsuario;
import br.com.fluxocaixa.projetotcc.repository.Filter.ProgressoUsuarioFilter;
import br.com.fluxocaixa.projetotcc.repository.ProgressoUsuario.ProgressoUsuarioRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.ProgressoUsuarioRepository;
import br.com.fluxocaixa.projetotcc.service.ProgressoUsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requestController")
public class ProgressoUsuarioController {

    @Autowired
    private ProgressoUsuarioRepository repository;

    @Autowired
    private ProgressoUsuarioRepositoryImpl repositoryimpl;

    @Autowired
    private ProgressoUsuarioService service;

    @GetMapping
    public List<ProgressoUsuario> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<ProgressoUsuarioDto> listarPorNome(ProgressoUsuarioFilter progressoUsuarioFilter, Pageable pageable){
        return repositoryimpl.filtrar(progressoUsuarioFilter, pageable);
    }

    @GetMapping("/{progressoUsuarioId}")
    public ProgressoUsuario buscar(@PathVariable Long progressoUsuarioId ){
        return service.buscaroufalhar(progressoUsuarioId);
    }

    @PostMapping
    public ProgressoUsuario adicionar(@RequestBody ProgressoUsuario progressoUsuario) { return service.salvar(progressoUsuario); }

    @DeleteMapping("/{progressoUsuarioId}")
    public void remover(@PathVariable Long progressoUsuarioId){ service.excluir(progressoUsuarioId);}

    @PutMapping("/{progressoUsuarioId}")
    public ProgressoUsuario alterar(@PathVariable Long progressoUsuarioId, @RequestBody ProgressoUsuario progressoUsuario){
        ProgressoUsuario progressoUsuarioAtual = service.buscaroufalhar(progressoUsuarioId);

        BeanUtils.copyProperties(progressoUsuario, progressoUsuarioAtual, "id");
        return service.salvar(progressoUsuarioAtual);
    }

}
