package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.CategoriaForum;
import br.com.fluxocaixa.projetotcc.repository.CategoriaForumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaForumService {

    @Autowired
    private CategoriaForumRepository categoriaForumRepository;

    @Transactional
    public CategoriaForum salvar(CategoriaForum categoriaForum) { return categoriaForumRepository.save(categoriaForum); }

    public CategoriaForum BouF(Long categoriaId){
        return categoriaForumRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrado com esse id"));
    }

    @Transactional
    public void excluir(Long categoriaId){ categoriaForumRepository.deleteById(categoriaId); }
}
