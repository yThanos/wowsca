package jwt.validation.wowsca.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jwt.validation.wowsca.model.Usuario;

public class JWTUtil {
    public static SecretKey secretKey = Keys.hmacShaKeyFor(generateSafeToken().getBytes(StandardCharsets.UTF_8));

    private static String generateSafeToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[36];
        random.nextBytes(bytes);
        var encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }

    public String geraToken(Usuario usuario){
        final Map<String, Object> claims = new HashMap<>();
        claims.put("sub", usuario.getUsername());
        claims.put("role", usuario.getPermissao());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+Duration.ofSeconds(400).toMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameToken(String token){
        if(token !=null){
            return this.parseToken(token).getSubject();
        }else{
            return null;
        }
    }

    public boolean isTokenExpirado(String token){

        if(token !=null){
            return this.parseToken(token).getExpiration().before(new Date());
        }else{
            return false;
        }
    }

    private Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token.replace("Bearer", ""))
                .getBody();
    }
    
}
