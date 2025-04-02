package vn.edu.hcmute.foodapp.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.edu.hcmute.foodapp.exception.InvalidDataException;
import vn.edu.hcmute.foodapp.service.JwtService;
import vn.edu.hcmute.foodapp.util.enumeration.ETokenType;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.accessToken.secretKey}")
    private String accessKey;

    @Value("${jwt.refreshToken.secretKey}")
    private String refreshKey;

    @Value("${jwt.verificationToken.secretKey}")
    private String verificationKey;

    @Value("${jwt.resetToken.secretKey}")
    private String resetKey;

    @Value("${jwt.accessToken.expiry}")
    private long accessTokenExpiry;

    @Value("${jwt.refreshToken.expiry}")
    private long refreshTokenExpiry;

    @Value("${jwt.verificationToken.expiry}")
    private long verificationTokenExpiry;

    @Value("${jwt.resetToken.expiry}")
    private long resetTokenExpiry;

    @Override
    public String generateAccessToken(UserDetails user) {
        if (user == null) throw new IllegalArgumentException("UserDetails cannot be null");
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", getRoles(user));
        return generateToken(claims, user.getUsername(), ETokenType.ACCESS_TOKEN);
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        if (user == null) throw new IllegalArgumentException("UserDetails cannot be null");
        return generateToken(new HashMap<>(), user.getUsername(), ETokenType.REFRESH_TOKEN);
    }

    @Override
    public String generateVerificationToken(String email) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Phone cannot be null or empty");
        return generateToken(new HashMap<>(), email, ETokenType.VERIFICATION_TOKEN);
    }

    @Override
    public String generateResetToken(UserDetails user) {
        if (user == null) throw new IllegalArgumentException("UserDetails cannot be null");
        return generateToken(new HashMap<>(), user.getUsername(), ETokenType.RESET_TOKEN);
    }

    @Override
    public String extractUsername(String token, ETokenType type){
        try {
            return extractClaim(token, type, Claims::getSubject);
        } catch (ExpiredJwtException e) {
            throw new InvalidDataException("Token has expired");
        } catch (SignatureException e) {
            throw new InvalidDataException("Invalid token signature");
        } catch (Exception e) {
            throw new InvalidDataException("Malformed token");
        }
    }

    @Override
    public boolean isValid(String token, ETokenType type, UserDetails userDetails){
        log.info("---------- isValid ----------");
        final String username = extractUsername(token, type);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, type));
    }

    @Override
    public boolean isTokenExpired(String token, ETokenType type){
        try {
            return extractExpiration(token, type).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            throw new InvalidDataException("Invalid token");
        }
    }

    @Override
    public long getRemainingTime(String token, ETokenType type) {
        try {
            Date expirationDate = extractExpiration(token, type);
            long currentTimeMillis = System.currentTimeMillis();
            long expirationTimeMillis = expirationDate.getTime();

            long remainingTimeMillis = expirationTimeMillis - currentTimeMillis;
            if (remainingTimeMillis <= 0) {
                return 0;
            }

            return remainingTimeMillis / 1000;
        } catch (ExpiredJwtException e) {
            return 0;
        } catch (Exception e) {
            throw new InvalidDataException("Invalid token");
        }
    }

    private String generateToken(Map<String, Object> claims, String subject, ETokenType type) {
        long expiryInMillis = getExpiry(type);
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiryInMillis))
                .signWith(getKey(type), Jwts.SIG.HS256)
                .compact();
    }

    private <T> T extractClaim(String token, ETokenType type, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, type);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, ETokenType type){
        return Jwts.parser().verifyWith(getKey(type)).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getKey(ETokenType type){
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

    private long getExpiry(ETokenType type) {
        return switch (type) {
            case ACCESS_TOKEN -> accessTokenExpiry;
            case REFRESH_TOKEN -> refreshTokenExpiry;
            case VERIFICATION_TOKEN -> verificationTokenExpiry;
            case RESET_TOKEN -> resetTokenExpiry;
            default -> throw new InvalidDataException("Invalid token type");
        };
    }


    private Date extractExpiration(String token, ETokenType type){
        return extractClaim(token, type, Claims::getExpiration);
    }

    private String getRoles(UserDetails user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}
