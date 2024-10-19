package com.jelwery.morri.Utils;

import com.jelwery.morri.Model.ROLE;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

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
//    public boolean validateToken(String token, String email) {
//        return extractEmail(token).equals(email) && !isTokenExpired(token);
//    }
    public String getEncodedSecret() {
        return Base64.getEncoder().encodeToString(secret.getBytes());
    }
//    public String extractEmail(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }

//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }

//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims =  Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
//        return claimsResolver.apply(claims);
//    }
//    public ROLE extractRole(String token) {
//        Claims claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
//        return ROLE.valueOf(claims.get("role").toString());
//    }

//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
    private Key getSigningKey() {
         byte[] secretBytes = secret.getBytes();
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
