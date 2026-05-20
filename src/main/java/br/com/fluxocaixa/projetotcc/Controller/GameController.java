package br.com.fluxocaixa.projetotcc.Controller;

import br.com.fluxocaixa.projetotcc.dto.ComentarioDto;
import br.com.fluxocaixa.projetotcc.dto.GameDto;
import br.com.fluxocaixa.projetotcc.model.Comentario;
import br.com.fluxocaixa.projetotcc.model.Game;
import br.com.fluxocaixa.projetotcc.repository.Filter.ComentarioFilter;
import br.com.fluxocaixa.projetotcc.repository.Filter.GameFilter;
import br.com.fluxocaixa.projetotcc.repository.GameRepository;
import br.com.fluxocaixa.projetotcc.service.GameService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/{gameId}")
    public Game buscar(@PathVariable Long gameId ){
        return service.BouF(gameId);
    }

    @PostMapping
    public Game adicionar(@RequestBody Game game) { return service.salvar(game); }

    @DeleteMapping("/{gameId}")
    public void remover(@PathVariable Long gameId){ service.excluir(gameId);}

    @PutMapping("/{gameId}")
    public Game alterar(@PathVariable Long gameId, @RequestBody Game game){
        Game gameAtual = service.BouF(gameId);

        BeanUtils.copyProperties(game, gameAtual, "id");
        return service.salvar(gameAtual);
    }

}
