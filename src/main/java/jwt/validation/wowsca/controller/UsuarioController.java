package jwt.validation.wowsca.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jwt.validation.wowsca.db.UsuarioDao;
import jwt.validation.wowsca.model.Usuario;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @GetMapping("/byEmail/{email}")
    public Usuario getUsuario(@PathVariable String email) {
        System.out.println("email: " + email);
        return new UsuarioDao().getUserByEmail(email);
    }

    @GetMapping("/byId/{id}")
    public Usuario getUsuarioById(@PathVariable int id) {
        return new UsuarioDao().getUserById(id);
    }

    @GetMapping("/all")
    public ArrayList<Usuario> getUsuarios() {
        return new UsuarioDao().getUsers();
    }

    @PostMapping("/criarConta")
    public void postUsuario(@RequestBody Usuario usuario) {
        new UsuarioDao().addUser(usuario);
    }

    @PutMapping("/{id}")
    public void putUsuario(@RequestBody Usuario usuario, @PathVariable int id) {
        new UsuarioDao().updateUser(usuario, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@RequestParam int id) {
        new UsuarioDao().deleteUser(id);
    }


}
