package br.com.fluxocaixa.projetotcc.config;

import br.com.fluxocaixa.projetotcc.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    // Vem de application.properties (api.security.token.secret), configurável via variável
    // de ambiente JWT_SECRET. Antes gerava uma chave aleatória a cada boot, o que invalidava
    // todos os tokens emitidos sempre que a aplicação reiniciava.
    @Value("${api.security.token.secret}")
    private String secret;

    private SecretKey chave() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(chave(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extrairEmailDoToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(chave())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
