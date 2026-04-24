package br.com.fluxocaixa.projetotcc.Controller;
import br.com.fluxocaixa.projetotcc.dto.UserDto;
import br.com.fluxocaixa.projetotcc.model.User;
import br.com.fluxocaixa.projetotcc.repository.Filter.UserFilter;
import br.com.fluxocaixa.projetotcc.repository.User.UserRepositoryImpl;
import br.com.fluxocaixa.projetotcc.repository.UserRepository;
import br.com.fluxocaixa.projetotcc.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userController")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserRepositoryImpl repositoryimpl;

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> listar() {
        return repository.findAll();
    }

    @GetMapping("/pornome")
    public Page<UserDto> listarPorNome(UserFilter userFilter, Pageable pageable){
        return repositoryimpl.filtrar(userFilter, pageable);
    }

    @GetMapping("/{userId}")
    public User buscar(@PathVariable Long userId ){
        return service.buscaroufalhar(userId);
    }

    @PostMapping
    public User adicionar(@RequestBody User user) { return service.salvar(user); }

    @DeleteMapping("/{userId}")
    public void remover(@PathVariable Long userId){ service.excluir(userId);}

    @PutMapping("/{userId}")
    public User alterar(@PathVariable Long userId, @RequestBody User user){
        User userAtual = service.buscaroufalhar(userId);

        BeanUtils.copyProperties(user, userAtual, "id");
        return service.salvar(userAtual);
    }

}
