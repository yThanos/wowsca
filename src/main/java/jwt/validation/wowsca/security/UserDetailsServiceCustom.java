package jwt.validation.wowsca.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jwt.validation.wowsca.db.UsuarioDao;
import jwt.validation.wowsca.model.Usuario;

@Service
public class UserDetailsServiceCustom implements UserDetailsService{

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = new UsuarioDao().getUser(username);
        if(user != null){
            return User.withUsername(user.getUsername()).password(user.getPassword()).authorities(user.getPermissao()).build();
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }
    
}
