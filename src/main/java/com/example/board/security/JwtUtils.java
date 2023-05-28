package com.example.board.security;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 24 hours
    private static final Set<String> invalidJWTSet = new HashSet<>();
    private static final ScheduledExecutorService service= Executors.newScheduledThreadPool(0);
    

    public static String generateToken(String username) {
        
        return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SECRET_KEY)
        .compact();
    }

    public static boolean validateToken(String token){
        if(invalidJWTSet.contains(token)){
            return false;
        }
        try{
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
            .parseClaimsJws(token);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public static void invalidJWT(String token){
        invalidJWTSet.add(token);
        service.schedule(()->invalidJWTSet.remove(token), EXPIRATION_TIME, TimeUnit.MILLISECONDS);
    }

    public static String getUsername(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
        .parseClaimsJws(token)
        .getBody();
        return claims.getSubject();
    }
}
