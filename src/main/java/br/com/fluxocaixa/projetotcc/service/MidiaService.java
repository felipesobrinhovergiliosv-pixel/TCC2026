package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.Midia;
import br.com.fluxocaixa.projetotcc.repository.MidiaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MidiaService {

    @Autowired
    private MidiaRepository midiaRepository;

    @Transactional
    public Midia salvar(Midia midia){ return midiaRepository.save(midia); }

    public Midia buscaroufalhar(Long midiaId){
        return midiaRepository.findById(midiaId)
                .orElseThrow(() -> new EntityNotFoundException("Midia não encontrada com esse Id"));
    }

    @Transactional
    public void excluir(Long midiaId){ midiaRepository.deleteById(midiaId); }
}
