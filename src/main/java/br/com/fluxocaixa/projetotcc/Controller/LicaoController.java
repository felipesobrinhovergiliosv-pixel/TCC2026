package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.LicaoDto;
import br.com.fluxocaixa.projetotcc.dto.LicaoRespostaDto;
import br.com.fluxocaixa.projetotcc.dto.LicaoRespostaResultadoDto;
import br.com.fluxocaixa.projetotcc.model.Licao;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.Filter.LicaoFilter;
import br.com.fluxocaixa.projetotcc.repository.Licao.LicaoRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.LicaoRepository;
import br.com.fluxocaixa.projetotcc.service.LicaoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<Licao> listar(@AuthenticationPrincipal User usuarioLogado) {
        List<Licao> licoes = repository.findAll();
        if (!ehAdmin(usuarioLogado)) {
            licoes.forEach(licao -> licao.setConteudo(service.conteudoPublico(licao.getConteudo())));
        }
        return licoes;
    }

    @GetMapping("/pornome")
    public Page<LicaoDto> listarPorNome(LicaoFilter licaoFilter, Pageable pageable, @AuthenticationPrincipal User usuarioLogado){
        Page<LicaoDto> pagina = repository.filtrar(licaoFilter, pageable);
        if (!ehAdmin(usuarioLogado)) {
            pagina.getContent().forEach(dto -> dto.setConteudo(service.conteudoPublico(dto.getConteudo())));
        }
        return pagina;
    }

    @GetMapping("/{licaoId}")
    public Licao buscar(@PathVariable Long licaoId, @AuthenticationPrincipal User usuarioLogado){
        Licao licao = service.buscaroufalhar(licaoId);
        if (!ehAdmin(usuarioLogado)) {
            licao.setConteudo(service.conteudoPublico(licao.getConteudo()));
        }
        return licao;
    }

    @PostMapping("/{licaoId}/responder")
    public LicaoRespostaResultadoDto responder(@PathVariable Long licaoId, @RequestBody @Valid LicaoRespostaDto dados){
        boolean correta = service.respostaCorreta(licaoId, dados.resposta());
        Licao licao = service.buscaroufalhar(licaoId);
        return new LicaoRespostaResultadoDto(correta, correta ? licao.getXp_recompensa() : 0);
    }

    @PostMapping
    public Licao adicionar(@RequestBody @Valid Licao licao) { return service.salvar(licao); }

    @DeleteMapping("/{licaoId}")
    public void remover(@PathVariable Long licaoId){ service.excluir(licaoId);}

    @PutMapping("/{licaoId}")
    public Licao alterar(@PathVariable Long licaoId, @RequestBody @Valid Licao licao){
        Licao licaoAtual = service.buscaroufalhar(licaoId);

        BeanUtils.copyProperties(licao, licaoAtual, "id");
        return service.salvar(licaoAtual);
    }

    private boolean ehAdmin(User usuarioLogado) {
        return usuarioLogado != null && Boolean.TRUE.equals(usuarioLogado.getAdmin());
    }

}
