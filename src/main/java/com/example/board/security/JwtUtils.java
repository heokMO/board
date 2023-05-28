package com.example.board.security;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 24 hours
    private static final Set<String> JWTSet = new HashSet<>();

    public static String generateToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
        JWTSet.add(token);

        return token;
    }

    public static boolean validateToken(String token) {
        if (!JWTSet.contains(token)) {
            return false;
        }
        try {
            if(JWTSet.contains(token) &&
                Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                    .parseClaimsJws(token) != null)
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public static void invalidJWT(String token) {
        JWTSet.remove(token);
    }

    public static String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
