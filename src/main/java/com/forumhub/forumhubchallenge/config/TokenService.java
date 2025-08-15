package com.forumhub.forumhubchallenge.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            keyBytes = Arrays.copyOf(keyBytes, 32);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getSubject(String tokenJWT) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(tokenJWT)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido ou expirado", e);
        }
    }

    public String gerarToken(UserDetails usuario) {
        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .setIssuer("API ForumHub")
                .claim("authorities", usuario.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setExpiration(Date.from(
                        LocalDateTime.now()
                                .plusHours(2)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extrairClaims(String tokenJWT) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(tokenJWT)
                .getBody();
    }




}