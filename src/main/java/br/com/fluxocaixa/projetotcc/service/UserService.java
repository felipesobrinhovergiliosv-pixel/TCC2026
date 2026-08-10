package br.com.fluxocaixa.projetotcc.service;

import br.com.fluxocaixa.projetotcc.dto.AlterarSenhaDto;
import br.com.fluxocaixa.projetotcc.dto.UserCadastroDto;
import br.com.fluxocaixa.projetotcc.model.Plano;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User cadastrar(UserCadastroDto dados) {
        User user = new User();
        user.setUser(dados.user());
        user.setEmail(dados.email());
        user.setSenha(passwordEncoder.encode(dados.senha()));
        user.setPlano(Plano.GRATIS);
        user.setDataCriacao(new Date());
        user.setAdmin(false);
        return userRepository.save(user);
    }

    // Usado só pra edição de dados já existentes (nome, e-mail, descr...): a senha aqui já vem
    // com o hash antigo (o Controller exclui "senha" do BeanUtils.copyProperties), então NÃO
    // deve ser recriptografada. Trocar senha é responsabilidade do alterarSenha().
    @Transactional
    public User salvar(User user){
        return userRepository.save(user);
    }

    @Transactional
    public User alterarSenha(Long userId, AlterarSenhaDto dados) {
        User usuario = buscaroufalhar(userId);

        if (!passwordEncoder.matches(dados.senhaAtual(), usuario.getSenha())) {
            throw new BadCredentialsException("Senha atual incorreta.");
        }

        usuario.setSenha(passwordEncoder.encode(dados.novaSenha()));
        return userRepository.save(usuario);
    }

    public User buscaroufalhar(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User não encontrado com esse Id"));
    }

    @Transactional
    public void excluir(Long userId){ userRepository.deleteById(userId); }

    // Só o próprio usuário (ou um admin) pode alterar/excluir a conta.
    public void validarDono(Long userId, User usuarioLogado) {
        boolean ehDono = userId.equals(usuarioLogado.getId());
        boolean ehAdmin = Boolean.TRUE.equals(usuarioLogado.getAdmin());

        if (!ehDono && !ehAdmin) {
            throw new AccessDeniedException("Você não tem permissão para alterar este usuário.");
        }
    }
}
