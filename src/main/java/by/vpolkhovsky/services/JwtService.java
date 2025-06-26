package by.vpolkhovsky.services;

import by.vpolkhovsky.config.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

    private static final long ACCESS_TOKEN_VALIDITY = 86400000;

    private ApplicationProperties properties;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(properties.getJwt().secret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        var currentTime = new Date();
        return Jwts.builder()
            .claims()
            .issuedAt(currentTime)
            .expiration(new Date(currentTime.getTime() + ACCESS_TOKEN_VALIDITY))
            .and()
            .subject(userDetails.getUsername())
            .signWith(secretKey)
            .compact();
    }

    public boolean validateToken(String token) {
        if (StringUtils.isBlank(extractUsername(token))) {
            throw new IllegalArgumentException("null username");
        }

        var expiration = extractExpiration(token);
        if (expiration == null) {
            throw new IllegalArgumentException("null expiration date");
        }
        if (expiration.before(new Date())) {
            throw new IllegalArgumentException("expired token");
        }

        return true;
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
        return Jwts.parser().decryptWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}
