package me.apjung.backend.service.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import me.apjung.backend.domain.user.User;

import javax.crypto.SecretKey;
import java.util.Date;

public abstract class BaseTokenProvider implements JwtTokenProvider {
    protected abstract String getSecret();
    protected abstract Long getExpirationTimeMilliSec();

    public String createToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecret()));
        Date exp = new Date((new Date()).getTime() + getExpirationTimeMilliSec());

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setAudience(user.getEmail())
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecret()));

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return Long.parseLong(jws.getBody().getSubject());
    }

    public boolean verifyToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecret()));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }
}
