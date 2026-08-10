package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.Licao;
import br.com.fluxocaixa.projetotcc.repository.LicaoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LicaoService {

    @Autowired
    private LicaoRepository licaoRepository;

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
}
