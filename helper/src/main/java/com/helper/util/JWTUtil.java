package com.helper.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    private final String SECRET="This-is-a-super-secret-key-for-jwt-token-generation";
    private final SecretKey key=Keys.hmacShaKeyFor(SECRET.getBytes());
    public String getJwtToken(String UserEmail)
    {
        return Jwts.builder()
            .setSubject(UserEmail)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
            .signWith(key,SignatureAlgorithm.HS256)
            .compact();
    }

    public String getUsernameFromToken(String token)
    {
        Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
        return claims.getSubject();
    }
}
