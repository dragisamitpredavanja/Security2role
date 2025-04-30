package com.sbs.ald.util;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.sbs.ald.dto.Role;
import com.sbs.ald.dto.User;

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
            .claim("roles", user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public String createToken(String username, List<Role> roles) {
		Claims claims = Jwts.claims().setSubject(username);
//		claims.put(ROLES_KEY, roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority()))
//				.filter(Objects::nonNull).collect(Collectors.toList()));

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
}
