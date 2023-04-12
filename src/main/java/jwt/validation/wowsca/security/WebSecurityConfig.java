package jwt.validation.wowsca.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    @Bean
    public FiltroAutenticacao filtroAutenticacao() throws Exception{
        return new FiltroAutenticacao();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity htpp) throws Exception{
        htpp.csrf().disable()
            .authorizeHttpRequests((auth)-> auth
            .requestMatchers(HttpMethod.POST, "/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/usuario/criarConta").permitAll()
            .requestMatchers(HttpMethod.GET, "/usuario/byId/{id}").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/usuario/byEmail/{email}").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/usuario/all").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers(HttpMethod.PUT, "/usuario").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/usuario").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/user").hasAuthority("USER")
            .requestMatchers(HttpMethod.GET, "/admin").hasAuthority("ADMIN"));
        
        htpp.addFilterBefore(this.filtroAutenticacao(), UsernamePasswordAuthenticationFilter.class);
        
        return htpp.build();
    }
}
