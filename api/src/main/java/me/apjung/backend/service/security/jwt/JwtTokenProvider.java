package me.apjung.backend.service.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.property.JwtProps;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenProvider {
    public static String createToken(User user, JwtProps.TokenProps tokenProps) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenProps.getSecret()));
        Date exp = new Date((new Date()).getTime() + tokenProps.getExpirationTimeMilliSec());

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setAudience(user.getEmail())
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    public static Long getUserIdFromToken(String token, JwtProps.TokenProps tokenProps) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenProps.getSecret()));

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return Long.parseLong(jws.getBody().getSubject());
    }

    public static boolean verifyToken(String token, JwtProps.TokenProps tokenProps) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenProps.getSecret()));
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
