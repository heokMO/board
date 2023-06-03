package com.example.board.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtSupporter {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = Duration.ofDays(1).toMillis();
    private final Set<String> JWTSet = new HashSet<>();

    public String generateToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
        JWTSet.add(token);
        
        return token;
    }

    public boolean validateToken(String token) {
        return JWTSet.contains(token) &&
                Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                        .parseClaimsJws(token) != null;
    }

    public void invalidJWT(String token) {
        JWTSet.remove(token);
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void cleanJWTSet() {
        final Date now = Date.from(Instant.now());
        JWTSet.removeIf(token -> Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody().getExpiration().before(now));
    }

    public String refreshJWT(String token) {
        String username = getUsername(token);
        return generateToken(username);
    }
}
