package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.ProgressoUsuario;
import br.com.fluxocaixa.projetotcc.repository.ProgressoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgressoUsuarioService {

    @Autowired
    private ProgressoUsuarioRepository progressoUsuarioRepository;

    @Transactional
    public ProgressoUsuario salvar(ProgressoUsuario progressoUsuario){ return progressoUsuarioRepository.save(progressoUsuario); }

    public ProgressoUsuario buscaroufalhar(Long progressoId){
        return progressoUsuarioRepository.findById(progressoId)
                .orElseThrow(() -> new EntityNotFoundException("Progresso não encontrado com esse Id"));
    }

    @Transactional
    public void excluir(Long progressoId){ progressoUsuarioRepository.deleteById(progressoId); }
}
