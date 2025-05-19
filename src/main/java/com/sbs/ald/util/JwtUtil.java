package com.sbs.ald.util;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sbs.ald.dto.Role;
import com.sbs.ald.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	private final String ROLES_KEY = "roles";
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;
    

    public String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("userId", user.getId())          // dodaj userId
            .claim("email", user.getEmail()) 
            .claim("roles", user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public String createToken(String username, String email, List<Role> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("email", email); // dodaj email

		claims.put(
			    ROLES_KEY, 
			    roles.stream()
			         .map(Role::getName)
			         .filter(Objects::nonNull)
			         .collect(Collectors.toList())
			);

		Date now = new Date();
		Date expiresAt = new Date(now.getTime() + expiration);
		String s = Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expiresAt)
				.signWith(SignatureAlgorithm.HS256, secret).compact();
		
		return s;
	}
    
    //ovaj deo za refresh token
    
    public String generateToken(String username, long expirationMs) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    public String generateRefreshToken(String username) {
        return generateToken(username, 1000L * 60 * 60 * 24 * 7); // 7 dana
    }
    //ovaj deo za refresh token
    
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
 // Ekstraktuj bilo koji claim
    public String extractClaim(String token, String claimKey) {
        Claims claims = extractAllClaims(token);
        return claims.get(claimKey, String.class);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
    }

}
