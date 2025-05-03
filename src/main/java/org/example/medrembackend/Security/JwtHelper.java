package org.example.medrembackend.Security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {

    private final Logger logger = LoggerFactory.getLogger(JwtHelper.class);

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private final String secret = "your-very-long-64-byte-minimum-secret-key-goes-here-1234567890abcdef"; // must be 64+ chars
    private final Key secretKey;

    public JwtHelper() {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
        logger.info("JwtHelper initialized with secret key");
    }


    // Retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        logger.debug("Getting username from token");
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        logger.debug("Getting expiration date from token");
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        logger.debug("Getting claim from token");
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        logger.debug("Getting all claims from token");
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        logger.debug("Checking if token is expired");
        final Date expiration = getExpirationDateFromToken(token);
        boolean expired = expiration.before(new Date());
        logger.debug("Token expired: {}", expired);
        return expired;
    }

    public String generateToken(UserDetails userDetails) {
        logger.info("Generating token for user: {}", userDetails.getUsername());
        Map<String, Object> claims = new HashMap<>();
        String token = doGenerateToken(claims, userDetails.getUsername());
        logger.info("Token generated");
        return token;
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        logger.debug("Doing generate token");
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        logger.info("Validating token for user: {}", userDetails.getUsername());
        final String username = getUsernameFromToken(token);
        boolean valid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        logger.info("Token valid: {}", valid);
        return valid;
    }
}
