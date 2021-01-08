package me.apjung.backend.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.property.JwtProps;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class RefreshTokenProvider implements JwtTokenProvider {
    private final JwtProps jwtProps;

    public String createToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProps.getRefreshToken().getSecret()));
        Date exp = new Date((new Date()).getTime() + jwtProps.getRefreshToken().getExpirationTimeMilliSec());

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setAudience(user.getEmail())
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProps.getRefreshToken().getSecret()));

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return Long.parseLong(jws.getBody().getSubject());
    }

    public boolean verifyToken(String token) throws RuntimeException {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProps.getRefreshToken().getSecret()));
        Jwts.parserBuilder().setSigningKey(key).build().parse(token);
        return true;
    }
}
