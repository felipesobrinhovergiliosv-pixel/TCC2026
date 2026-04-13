package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.CategoriaForum;
import br.com.fluxocaixa.projetotcc.repository.CategoriaForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaForumService {

    @Autowired
    private CategoriaForumRepository categoriaForumRepository;

    @Transactional
    public CategoriaForum salvar(CategoriaForum categoriaForum) { return categoriaForumRepository.save(categoriaForum); }

    // public
}
