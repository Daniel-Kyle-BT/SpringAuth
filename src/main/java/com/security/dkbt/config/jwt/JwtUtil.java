package com.security.dkbt.config.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final JwtProperties properties;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
	}

	public String generarToken(JwtUserDetails  user) {
		Map<String, Object> claims = new HashMap<>();

		claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
		claims.put("emp_id", user.getEmpleadoId());

		return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + properties.getExpiration()))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}
	
	public long getExpirationSeconds() {
	    return properties.getExpiration() / 1000;
	}

	public Claims parse(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
}
