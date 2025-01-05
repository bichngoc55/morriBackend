package com.jelwery.morri.Utils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.jelwery.morri.Model.ROLE;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWT {
//    @Value("${jwt.secret}")
//    private String secret;
    final private String secret = "abcdefghijklmnopqrstuvwxyz123456";
    public String generateToken(String email, ROLE role) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim("role", role.name())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes()).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }
    public boolean validateToken(String token) {
        final String email = extractEmail(token);
        return (  !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    } 
    public String getEncodedSecret() {
        return Base64.getEncoder().encodeToString(secret.getBytes());
    }
 
    private Key getSigningKey() {
         byte[] secretBytes = secret.getBytes();
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
