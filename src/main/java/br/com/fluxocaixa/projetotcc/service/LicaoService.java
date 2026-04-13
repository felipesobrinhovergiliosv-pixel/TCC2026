package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.Licao;
import br.com.fluxocaixa.projetotcc.repository.LicaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LicaoService {

    @Autowired
    private LicaoRepository licaoRepository;

    @Transactional
    public Licao salvar(Licao licao){ return licaoRepository.save(licao); }

    public Licao BouF(Long licaoId){
        return licaoRepository.findById(licaoId)
                .orElseThrow(() -> new EntityNotFoundException("Lição não encontrada com esse Id"));
    }

    @Transactional
    public void excluir(Long licaoId){ licaoRepository.deleteById(licaoId); }
}
