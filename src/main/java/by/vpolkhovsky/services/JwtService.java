package by.vpolkhovsky.services;

import by.vpolkhovsky.config.ApplicationProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final long ACCESS_TOKEN_VALIDITY = 86400000; // 24 hours in milliseconds

    private final ApplicationProperties properties;
    private SecretKey secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        String secret = properties.getJwt().secret();
        if (StringUtils.isBlank(secret)) {
            throw new IllegalArgumentException("JWT secret is not configured");
        }
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String generateToken(UserDetails userDetails) {
        var currentTime = new Date();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(currentTime)
                .expiration(new Date(currentTime.getTime() + ACCESS_TOKEN_VALIDITY))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !isTokenExpired(claims) &&
                    StringUtils.isNotBlank(claims.getSubject());
        } catch (SecurityException | MalformedJwtException |
                 ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}