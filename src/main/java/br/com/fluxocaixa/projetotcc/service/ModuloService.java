package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.Modulo;
import br.com.fluxocaixa.projetotcc.repository.ModuloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    @Transactional
    public Modulo salvar(Modulo modulo){ return moduloRepository.save(modulo); }

    public Modulo buscaroufalhar(Long moduloId){
        return moduloRepository.findById(moduloId)
                .orElseThrow(() -> new EntityNotFoundException("Modulo não encontrado com esse Id"));
    }

    @Transactional
    public void excluir(Long moduloId){ moduloRepository.deleteById(moduloId); }
}
