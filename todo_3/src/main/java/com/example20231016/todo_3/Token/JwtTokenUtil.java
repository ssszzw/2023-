package com.example20231016.todo_3.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.function.Function;



@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access_token.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh_token.expiration}")
    private Long refreshTokenExpiration;

    public String generateAccessToken(String user_id) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user_id, accessTokenExpiration);
    }

    public String generateRefreshToken(String user_id) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user_id, refreshTokenExpiration);
    }

    private String doGenerateToken(Map<String, Object> claims, String user_id, Long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user_id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public int getUserIdFromToken(String token) {
        String idStr=getClaimFromToken(token, Claims::getSubject);
        return Integer.parseInt(idStr);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
