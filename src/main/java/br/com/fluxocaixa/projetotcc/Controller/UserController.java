package br.com.fluxocaixa.projetotcc.Controller;
import br.com.fluxocaixa.projetotcc.dto.AlterarSenhaDto;
import br.com.fluxocaixa.projetotcc.dto.UserCadastroDto;
import br.com.fluxocaixa.projetotcc.dto.UserDto;
import br.com.fluxocaixa.projetotcc.dto.UserPublicoDto;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.Filter.UserFilter;
import br.com.fluxocaixa.projetotcc.repository.User.UserRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.UserRepository;
import br.com.fluxocaixa.projetotcc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    // Lista completa de usuários é restrita a admin (ver SecurityConfig); acesso a um usuário
    // específico continua liberado via GET /user/{userId}.
    @GetMapping
    public List<User> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<UserDto> listarPorNome(UserFilter userFilter, Pageable pageable){
        return repository.filtrar(userFilter, pageable);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> buscar(@PathVariable Long userId, @AuthenticationPrincipal User usuarioLogado){
        User usuario = service.buscaroufalhar(userId);

        boolean ehDono = usuarioLogado != null && userId.equals(usuarioLogado.getId());
        boolean ehAdmin = usuarioLogado != null && Boolean.TRUE.equals(usuarioLogado.getAdmin());

        if (ehDono || ehAdmin) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.ok(new UserPublicoDto(usuario.getId(), usuario.getUser(), usuario.getDescr()));
    }

    // DTO próprio pro cadastro: impede que o cliente mande "plano" ou "admin" no corpo
    // da requisição e vire premium/admin de graça.
    @PostMapping
    public User adicionar(@RequestBody @Valid UserCadastroDto dados) { return service.cadastrar(dados); }

    @DeleteMapping("/{userId}")
    public void remover(@PathVariable Long userId, @AuthenticationPrincipal User usuarioLogado){
        service.validarDono(userId, usuarioLogado);
        service.excluir(userId);
    }

    @PutMapping("/{userId}")
    public User alterar(@PathVariable Long userId, @RequestBody User user, @AuthenticationPrincipal User usuarioLogado){
        service.validarDono(userId, usuarioLogado);

        User userAtual = service.buscaroufalhar(userId);
        // "senha", "plano" e "admin" não são editáveis por aqui: senha tem endpoint próprio
        // (PUT /user/{id}/senha) e plano/admin só via processo de pagamento/promoção.
        BeanUtils.copyProperties(user, userAtual, "id", "senha", "plano", "admin");
        return service.salvar(userAtual);
    }

    @PutMapping("/{userId}/senha")
    public User alterarSenha(@PathVariable Long userId, @RequestBody @Valid AlterarSenhaDto dados, @AuthenticationPrincipal User usuarioLogado){
        service.validarDono(userId, usuarioLogado);
        return service.alterarSenha(userId, dados);
    }

}
