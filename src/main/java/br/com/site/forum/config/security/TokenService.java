package br.com.site.forum.config.security;

import br.com.site.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String segredo;

    public String gerarToken(Authentication authenticate) {
        Usuario user = (Usuario) authenticate.getPrincipal();

        Date hoje = new Date();
        Date expiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer(user.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS256, segredo)
                .compact();
    }

    public Boolean isValid(String token) {
        try{
            Jwts.parser().setSigningKey(this.segredo).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Long getUserId(String token) {
        Claims body = Jwts.parser().setSigningKey(this.segredo).parseClaimsJws(token).getBody();
        return Long.parseLong(body.getIssuer());
    }
}
