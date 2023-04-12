package jwt.validation.wowsca.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FiltroAutenticacao extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
        String url = request.getRequestURI();
        if(!url.contains("/login") && !url.contains("/criarConta")){
            try{
                String token = request.getHeader("Authorization");
                if(token == null || !token.startsWith("Bearer ")){
                    response.sendError(401);
                }
                String username = new JWTUtil().getUsernameToken(token);
                if(username == null && new JWTUtil().isTokenExpirado(token)){
                    response.sendError(401);
                }
                if(SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = new UserDetailsServiceCustom().loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                response.sendError(401);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
