package com.example.ecommerce_spring.services;


import com.example.ecommerce_spring.config.JwtConfiguration;
import com.example.ecommerce_spring.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfiguration jwtConfig;


    public String generateAccessToken(User user) {

        final long tokenExpiration = jwtConfig.getAccessTokenExpiration(); // 5 mins

        return generateToken(user, tokenExpiration);
    }
    public String generateRefreshToken(User user) {

        final long tokenExpiration = jwtConfig.getRefreshTokenExpiration(); // 7 day

        return generateToken(user, tokenExpiration);
    }


    private String generateToken(User user, long tokenExpiration) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .compact();
    }

    public boolean validateToken(String token){
        try{
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());

        } catch (Exception e) {
            return false;
        }
    }
    public Long getIdFromToken(String token){
        return Long.valueOf(getClaims(token).getSubject());
    }

    private Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}