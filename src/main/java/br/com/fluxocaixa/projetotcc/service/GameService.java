package br.com.fluxocaixa.projetotcc.service;


import br.com.fluxocaixa.projetotcc.model.Game;
import br.com.fluxocaixa.projetotcc.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    @Transactional
    public Game salvar(Game game){ return  repository.save(game); }

    public Game BouF(Long gameId){
        return repository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Informação jogo não encontrado com esse id"));
    }

    @Transactional
    public void excluir(Long gameId){ repository.deleteById((gameId));}
}
