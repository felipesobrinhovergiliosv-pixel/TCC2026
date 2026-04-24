package br.com.fluxocaixa.projetotcc.config;

import br.com.fluxocaixa.projetotcc.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    private final SecretKey CHAVE = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String gerarToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(CHAVE)
                .compact();
    }

    public String extrairEmailDoToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(CHAVE)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
