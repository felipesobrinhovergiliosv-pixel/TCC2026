package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.ProgressoUsuarioDto;
import br.com.fluxocaixa.projetotcc.model.ProgressoUsuario;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.Filter.ProgressoUsuarioFilter;
import br.com.fluxocaixa.projetotcc.repository.ProgressoUsuario.ProgressoUsuarioRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.ProgressoUsuarioRepository;
import br.com.fluxocaixa.projetotcc.service.ProgressoUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progressoUsuario")
public class ProgressoUsuarioController {

    @Autowired
    private ProgressoUsuarioRepository repository;

    @Autowired
    private ProgressoUsuarioService service;

    @GetMapping
    public List<ProgressoUsuario> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<ProgressoUsuarioDto> listarPorNome(ProgressoUsuarioFilter progressoUsuarioFilter, Pageable pageable){
        return repository.filtrar(progressoUsuarioFilter, pageable);
    }

    @GetMapping("/meu")
    public List<ProgressoUsuario> meuProgresso(@AuthenticationPrincipal User usuarioLogado){
        return repository.findByUserId(usuarioLogado.getId());
    }

    @GetMapping("/{progressoUsuarioId}")
    public ProgressoUsuario buscar(@PathVariable Long progressoUsuarioId, @AuthenticationPrincipal User usuarioLogado){
        ProgressoUsuario progresso = service.buscaroufalhar(progressoUsuarioId);
        service.validarDono(progresso, usuarioLogado);
        return progresso;
    }

    @PostMapping
    public ProgressoUsuario adicionar(@RequestBody @Valid ProgressoUsuario progressoUsuario, @AuthenticationPrincipal User usuarioLogado) {
        progressoUsuario.setUser(usuarioLogado);
        return service.salvar(progressoUsuario);
    }

    @DeleteMapping("/{progressoUsuarioId}")
    public void remover(@PathVariable Long progressoUsuarioId, @AuthenticationPrincipal User usuarioLogado){
        ProgressoUsuario progresso = service.buscaroufalhar(progressoUsuarioId);
        service.validarDono(progresso, usuarioLogado);
        service.excluir(progressoUsuarioId);
    }

    @PutMapping("/{progressoUsuarioId}")
    public ProgressoUsuario alterar(@PathVariable Long progressoUsuarioId, @RequestBody @Valid ProgressoUsuario progressoUsuario, @AuthenticationPrincipal User usuarioLogado){
        ProgressoUsuario progressoUsuarioAtual = service.buscaroufalhar(progressoUsuarioId);
        service.validarDono(progressoUsuarioAtual, usuarioLogado);

        BeanUtils.copyProperties(progressoUsuario, progressoUsuarioAtual, "id", "user");
        return service.salvar(progressoUsuarioAtual);
    }

}
