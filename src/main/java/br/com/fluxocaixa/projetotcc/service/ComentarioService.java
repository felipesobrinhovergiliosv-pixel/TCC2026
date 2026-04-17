package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.CategoriaForum;
import br.com.fluxocaixa.projetotcc.model.Comentario;
import br.com.fluxocaixa.projetotcc.repository.ComentarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Transactional
    public Comentario salvar(Comentario comentario){ return comentarioRepository.save(comentario); }

    public Comentario BouF(Long comentarioId){
        return comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("Comentario não encontrado com esse Id"));
    }

    public Comentario buscaroufalhar(Long comentarioId){
        return comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("Comentario não encontrado com esse id"));
    }

    @Transactional
    public void excluir(Long comentarioId){ comentarioRepository.deleteById(comentarioId); }

}
