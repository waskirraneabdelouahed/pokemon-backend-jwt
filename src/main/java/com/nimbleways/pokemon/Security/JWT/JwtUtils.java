package com.nimbleways.pokemon.Security.JWT;

import com.nimbleways.pokemon.UserDetails.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.util.Date;


@AllArgsConstructor
@Getter
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    public JwtUtils() {
    }

    @Value("${abdelouahed.app.jwtSecret}")
    private String jwtSecret;
    @Value("${abdelouahed.app.jwtExpirationMs}")
    private int jwtExpirationMs;


    public String generateJwtToken(Authentication authentication) {
        byte[] secretByte = jwtSecret.getBytes();
        Key key = Keys.hmacShaKeyFor(secretByte);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();


        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        byte[] secretByte = jwtSecret.getBytes();
        Key key = Keys.hmacShaKeyFor(secretByte);
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        String subject = claimsJws.getBody().getSubject();
        return subject;
    }

    public boolean validateJwtToken(String authToken) {
        byte[] secretByte = jwtSecret.getBytes();
        Key key = Keys.hmacShaKeyFor(secretByte);
        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
