package vn.edu.hcmute.foodapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.hcmute.foodapp.util.enumeration.ETokenType;

public interface JwtService {
    String generateAccessToken(UserDetails user);

    String generateRefreshToken(UserDetails user);

    String generateVerificationToken(String email);

    String generateResetToken(UserDetails user);

    String extractUsername(String token, ETokenType type);

    boolean isValid(String token, ETokenType type, UserDetails userDetails);

    boolean isTokenExpired(String token, ETokenType type);

    long getRemainingTime(String token, ETokenType type);
}
