package jwt.validation.wowsca.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jwt.validation.wowsca.model.Usuario;
import jwt.validation.wowsca.security.JWTUtil;
import jwt.validation.wowsca.security.UserDetailsServiceCustom;

@RequestMapping
@RestController
public class LoginController {
    @PostMapping("/login")
    public ResponseEntity<String> logar(@RequestBody Usuario usuario){
        UserDetails userDetails = new UserDetailsServiceCustom().loadUserByUsername(usuario.getUsername());
        if(userDetails != null){
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            usuario.setPermissao(authentication.getAuthorities().toString().replace("[", "").replace("]", ""));

            String token = new JWTUtil().geraToken(usuario);

            return new ResponseEntity<>(token, HttpStatusCode.valueOf(200));
        }

        return new ResponseEntity<>("Usuario ou senha invalidos!", HttpStatusCode.valueOf(401));
    }

    @GetMapping("/admin")
    public ResponseEntity<String> teste(){
        return new ResponseEntity<>("parabens voce é um ADMIN!", HttpStatusCode.valueOf(200));
    }
    @GetMapping("/user")
    public ResponseEntity<String> user(){
        return new ResponseEntity<>("Parabens voce é um USER!", HttpStatusCode.valueOf(2000));
    }
}
