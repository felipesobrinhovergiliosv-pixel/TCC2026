package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.GameDto;
import br.com.fluxocaixa.projetotcc.model.Game;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.Filter.GameFilter;
import br.com.fluxocaixa.projetotcc.repository.GameRepository;
import br.com.fluxocaixa.projetotcc.service.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameRepository repository;

    @Autowired
    private GameService service;

    @GetMapping
    public List<Game> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<GameDto> listarPorNome(GameFilter filter, Pageable pageable){
        return repository.filtrar(filter, pageable);
    }

    @GetMapping("/meu")
    public Game meuGame(@AuthenticationPrincipal User usuarioLogado){
        return service.buscarOuCriarDoUsuario(usuarioLogado);
    }

    @GetMapping("/{gameId}")
    public Game buscar(@PathVariable Long gameId, @AuthenticationPrincipal User usuarioLogado){
        Game game = service.BouF(gameId);
        service.validarDono(game, usuarioLogado);
        return game;
    }

    @PostMapping
    public Game adicionar(@AuthenticationPrincipal User usuarioLogado) {
        Game game = new Game();
        game.setUser(usuarioLogado);
        game.setVidas(5);
        game.setMoedas(0);
        game.setStreak(0);
        return service.salvar(game);
    }

    @DeleteMapping("/{gameId}")
    public void remover(@PathVariable Long gameId, @AuthenticationPrincipal User usuarioLogado){
        Game game = service.BouF(gameId);
        service.validarDono(game, usuarioLogado);
        service.excluir(gameId);
    }

    @PutMapping("/{gameId}")
    public Game alterar(@PathVariable Long gameId, @RequestBody @Valid Game game, @AuthenticationPrincipal User usuarioLogado){
        Game gameAtual = service.BouF(gameId);
        service.validarDono(gameAtual, usuarioLogado);

        BeanUtils.copyProperties(game, gameAtual, "id", "user", "vidas", "moedas", "streak");
        return service.salvar(gameAtual);
    }

}
