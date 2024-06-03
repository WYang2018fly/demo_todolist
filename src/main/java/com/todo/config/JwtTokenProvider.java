package com.todo.config;

import com.todo.constant.AuthExceptionConstant;
import com.todo.exception.AuthException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(jwtSecret)).compact();
    }

    public String getUserFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(Base64.getDecoder().decode(jwtSecret)).parseClaimsJws(token).getBody();
            log.info(String.valueOf(claims));
            return claims.getSubject();
        } catch (MalformedJwtException e) {
            throw new AuthException(AuthExceptionConstant.JWT_INVALID);
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthExceptionConstant.JWT_EXPIRED);
        }
    }
}
