package com.spring.andorinha.backend.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Chave secreta utilizada na geração e validação dos tokens JWT
    private static final String SECRET_KEY = "3272357538782F413F4428472B4B6250655368566D5971337336763979244226";

    // Método que extrai o nome de usuário de um token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Método genérico para extrair uma reivindicação (claim) do token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Extrai todas as reivindicações (claims) do token
        final Claims claims = extractAllClaims(token);
        // Aplica a função claimsResolver para obter o valor desejado
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken (
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Método privado que extrai todas as reivindicações (claims) do token
    private Claims extractAllClaims (String token){
        // Cria um parser JWT e o configura com a chave de assinatura
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método privado que obtém a chave de assinatura a partir da chave secreta
    private Key getSignInKey() {
        byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
        // Cria uma chave HMAC a partir dos bytes da chave
        return Keys.hmacShaKeyFor(keyBites);
    }
}
