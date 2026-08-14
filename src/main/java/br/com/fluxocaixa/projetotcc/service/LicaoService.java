package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.dto.LicaoRespostaResultadoDto;
import br.com.fluxocaixa.projetotcc.model.Game;
import br.com.fluxocaixa.projetotcc.model.Licao;
import br.com.fluxocaixa.projetotcc.model.ProgressoUsuario;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.LicaoRepository;
import br.com.fluxocaixa.projetotcc.repository.ProgressoUsuarioRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class LicaoService {

    @Autowired
    private LicaoRepository licaoRepository;

    @Autowired
    private ProgressoUsuarioRepository progressoUsuarioRepository;

    @Autowired
    private GameService gameService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public Licao salvar(Licao licao){ return licaoRepository.save(licao); }

    public Licao buscaroufalhar(Long licaoId){
        return licaoRepository.findById(licaoId)
                .orElseThrow(() -> new EntityNotFoundException("Lição não encontrada com esse Id"));
    }

    @Transactional
    public void excluir(Long licaoId){ licaoRepository.deleteById(licaoId); }

    public String conteudoPublico(String conteudoJson) {
        if (conteudoJson == null) return null;
        try {
            JsonNode node = objectMapper.readTree(conteudoJson);
            if (node instanceof ObjectNode objectNode) {
                objectNode.remove("resposta");
            }
            return objectMapper.writeValueAsString(node);
        } catch (Exception e) {
            throw new IllegalStateException("Conteúdo da lição em formato inválido.", e);
        }
    }

    public boolean respostaCorreta(Long licaoId, String respostaEnviada) {
        Licao licao = buscaroufalhar(licaoId);
        try {
            JsonNode node = objectMapper.readTree(licao.getConteudo());
            String respostaCorreta = node.path("resposta").asText(null);
            return respostaCorreta != null && respostaCorreta.equalsIgnoreCase(respostaEnviada);
        } catch (Exception e) {
            throw new IllegalStateException("Conteúdo da lição em formato inválido.", e);
        }
    }

    @Transactional
    public LicaoRespostaResultadoDto responder(Long licaoId, String respostaEnviada, User usuarioLogado) {
        boolean correta = respostaCorreta(licaoId, respostaEnviada);
        Licao licao = buscaroufalhar(licaoId);

        ProgressoUsuario progresso = progressoUsuarioRepository
                .findByUserIdAndLicaoId(usuarioLogado.getId(), licaoId)
                .orElseGet(() -> {
                    ProgressoUsuario novo = new ProgressoUsuario();
                    novo.setUser(usuarioLogado);
                    novo.setLicao(licao);
                    return novo;
                });

        // Responder uma lição já concluída de novo não pode creditar XP outra vez —
        // sem essa checagem, reabrir uma lição feita e acertar de novo rendia moedas
        // infinitas.
        boolean jaConcluida = Boolean.TRUE.equals(progresso.getConcluido());
        int xp = (correta && !jaConcluida) ? licao.getXp_recompensa() : 0;

        if (correta && !jaConcluida) {
            Game game = gameService.buscarOuCriarDoUsuario(usuarioLogado);
            game.setMoedas(game.getMoedas() + xp);
            gameService.salvar(game);

            progresso.setConcluido(true);
            progresso.setDataConclusao(LocalDate.now());
            progressoUsuarioRepository.save(progresso);
        }

        return new LicaoRespostaResultadoDto(correta, xp);
    }
}
