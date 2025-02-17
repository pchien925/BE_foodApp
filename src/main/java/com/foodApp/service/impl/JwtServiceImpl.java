package com.foodApp.service.impl;

import com.foodApp.exception.InvalidDataException;
import com.foodApp.service.JwtService;
import com.foodApp.util.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiryHour}")
    private Long expiryHour;

    @Value("${jwt.expiryDay}")
    private Long expiryDay;

    @Value("${jwt.accessKey}")
    private String accessKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;

    @Value("${jwt.verificationKey}")
    private String verificationKey;

    @Value("${jwt.resetKey}")
    private String resetKey;

    @Override
    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        return generateRefreshToken(new HashMap<>(), user);
    }

    @Override
    public String generateVerificationToken(UserDetails user) {
        return generateVerificationToken(new HashMap<>(), user);
    }

    @Override
    public String generateResetToken(UserDetails user) {
        return generateResetToken(new HashMap<>(), user);
    }

    @Override
    public String extractUsername(String token, TokenType type){
        return extractClaim(token, type, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, TokenType type, UserDetails userDetails){
        log.info("---------- isValid ----------");
        final String username = extractUsername(token, type);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, type));
    }

    private String generateToken(Map<String, Object> claims, UserDetails user){
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryHour))
                .signWith(getKey(TokenType.ACCESS_TOKEN), Jwts.SIG.HS256)
                .compact();
    }

    private String generateRefreshToken(Map<String, Object> claims, UserDetails user){
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryDay))
                .signWith(getKey(TokenType.REFRESH_TOKEN), Jwts.SIG.HS256)
                .compact();
    }

    private String generateVerificationToken(Map<String, Object> claims, UserDetails user){
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .signWith(getKey(TokenType.VERIFICATION_TOKEN), Jwts.SIG.HS256)
                .compact();
    }

    private String generateResetToken(Map<String, Object> claims, UserDetails user){
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .signWith(getKey(TokenType.VERIFICATION_TOKEN), Jwts.SIG.HS256)
                .compact();
    }

    private <T> T extractClaim(String token, TokenType type, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, type);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, TokenType type){
        return Jwts.parser().verifyWith(getKey(type)).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getKey(TokenType type){
        switch (type) {
            case ACCESS_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
            }
            case REFRESH_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
            }
            case VERIFICATION_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(verificationKey));
            }
            case RESET_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(resetKey));
            }
            default -> throw new InvalidDataException("Invalid token type");
        }
    }

    private boolean isTokenExpired(String token, TokenType type){
        return extractExpiration(token, type).before(new Date());
    }

    private Date extractExpiration(String token, TokenType type){
        return extractClaim(token, type, Claims::getExpiration);
    }
}
