package jwt.validation.wowsca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jwt.validation.wowsca.model.Usuario;
import jwt.validation.wowsca.security.JWTUtil;

@RequestMapping
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Usuario> logar(@RequestBody Usuario usuario){
        final Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()));
        if(authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            usuario.setPermissao(authentication.getAuthorities().toString().replace("[", "").replace("]", ""));
            usuario.setToken(new JWTUtil().geraToken(usuario));
            return new ResponseEntity<>(usuario, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(401));
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin(){
        return new ResponseEntity<>("parabens voce é um ADMIN!", HttpStatusCode.valueOf(200));
    }
    @GetMapping("/user")
    public ResponseEntity<String> user(){
        return new ResponseEntity<>("Parabens voce é um USER!", HttpStatusCode.valueOf(200));
    }
}
