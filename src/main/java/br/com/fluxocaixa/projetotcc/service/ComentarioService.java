package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.Comentario;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.ComentarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Transactional
    public Comentario salvar(Comentario comentario){ return comentarioRepository.save(comentario); }

    public Comentario buscaroufalhar(Long comentarioId){
        return comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("Comentario não encontrado com esse Id"));
    }

    @Transactional
    public void excluir(Long comentarioId){ comentarioRepository.deleteById(comentarioId); }

    // Só o autor do comentário (ou um admin) pode editar/apagar.
    public void validarDono(Comentario comentario, User usuarioLogado) {
        boolean ehDono = comentario.getUser().getId().equals(usuarioLogado.getId());
        boolean ehAdmin = Boolean.TRUE.equals(usuarioLogado.getAdmin());

        if (!ehDono && !ehAdmin) {
            throw new AccessDeniedException("Você não tem permissão para alterar este comentário.");
        }
    }
}
