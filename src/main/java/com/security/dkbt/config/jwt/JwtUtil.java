package com.security.dkbt.config.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.security.dkbt.infrastructure.persistence.entity.UsuarioEntity;

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

	public String generarToken(UsuarioEntity user) {
		Map<String, Object> claims = new HashMap<>();

		claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
		claims.put("emp_id", user.getIdEmpleado());

		return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + properties.getExpiration()))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public String obtenerUsername(String token) {
		return obtenerClaims(token).getSubject();
	}

	public Long obtenerEmpleadoId(String token) {
		return obtenerClaims(token).get("emp_id", Long.class);
	}

	public List<String> obtenerRoles(String token) {
		Object roles = obtenerClaims(token).get("roles");

		if (roles instanceof List<?> list) {
			return list.stream().map(Object::toString).toList();
		}

		return List.of();
	}

	public boolean validarToken(String token) {
		Claims claims = obtenerClaims(token);
		return !claims.getExpiration().before(new Date());
	}

	private Claims obtenerClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
}
