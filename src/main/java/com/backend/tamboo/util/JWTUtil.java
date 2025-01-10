package com.backend.tamboo.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import java.security.Key;


@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    public String generateToken(Integer userId) {
        return Jwts.builder()
                .setSubject(userId.toString()) // Usa l'ID come subject
                .claim("userId", userId)       // Inserisce l'ID utente come claim
                .setIssuedAt(new Date())          // Data di creazione
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Scadenza
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Firma il token
                .compact();
    }

    public Integer extractEmployeeId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Key getSigningKey() {
        // Decodifica la chiave segreta da Base64
        byte[] keyBytes = java.util.Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
