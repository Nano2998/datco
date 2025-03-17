package com.example.datco.common;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtUtils {
    private final SecretKey secretKey;

    @Value("${spring.jwt.expires}")
    private long expiresNormal;

    @Value("${spring.jwt.refresh.expires}")
    private long expiresRefresh;

    public JwtUtils(@Value("${spring.jwt.secret}") String key) {
        secretKey = new SecretKeySpec(key.getBytes(), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String makeJwtToken(Long id) {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiresNormal))
                .claim("type", "access")
                .claim("id", id)
                .signWith(secretKey)
                .compact();
    }

    public String makeRefreshToken(Long id) {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiresRefresh))
                .claim("type", "refresh")
                .claim("id", id)
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(token).getPayload().get("name", String.class);
    }

    public Long getId(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(token).getPayload().get("id", Long.class);
    }

    public String getType(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(token).getPayload().get("type", String.class);
    }

    public boolean isExpired(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(token).getPayload().getExpiration().before(new Date());
    }
}