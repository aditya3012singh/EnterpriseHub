package com.aditya.enterprisehub.auth.service;

import io.jsonwebtoken.*;
import lombok.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

//    @Value("${jwt.secret}")
    private String jwtSecret="nexura-super-secret-key-change-this";

//    @Value("${jwt.expiration}")
    private long jwtExpiration=86400000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return parseToken(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token){
        try{
            parseToken(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    private Jws<Claims> parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

}
