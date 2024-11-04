package com.jt.e_commerce_website_backend.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Method to generate a JWT
    public String generateToken(Authentication authentication, String email) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 8400000)) // Set expiration time
                .claim("name", authentication.getName())
                .claim("email", email) // Store email in the token
                .claim("authorities", roles)
                .signWith(key) // Sign the JWT with the secret key
                .compact();
    }

    // Method to extract email from JWT
    public String getEmailFromJwtToken(String token) {
        try {
            // Remove "Bearer " prefix if present
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("email", String.class); // Return the email from the claims
        } catch (Exception e) {
            return null; // Return null if there's an error (e.g., token is invalid)
        }
    }

    // Method to populate authorities from GrantedAuthority collection
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // Join roles with a comma
    }
}
