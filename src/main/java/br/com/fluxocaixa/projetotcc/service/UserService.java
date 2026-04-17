package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User salvar(User user){ return userRepository.save(user); }

    public User buscaroufalhar(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User não encontrado com esse Id"));
    }

    @Transactional
    public void excluir(Long userId){ userRepository.deleteById(userId); }
}
