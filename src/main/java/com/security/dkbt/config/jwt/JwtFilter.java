package com.security.dkbt.config.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.dkbt.exception.problem.ProblemFactory;
import com.security.dkbt.exception.problem.ProblemWriter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final ProblemFactory problemFactory;
	private final ProblemWriter problemWriter;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")
				&& SecurityContextHolder.getContext().getAuthentication() == null) {

			String token = header.substring(7);

			try {

				Claims claims = jwtUtil.parse(token);

				String username = claims.getSubject();
				Long empleadoId = claims.get("emp_id", Long.class);

				Collection<? extends GrantedAuthority> authorities = ((List<?>) claims.get("roles")).stream()
						.map(Object::toString).map(SimpleGrantedAuthority::new).toList();

				JwtUserDetails user = JwtUserDetails.fromToken(username, empleadoId, authorities);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
						authorities);

				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (JwtException ex) {
				SecurityContextHolder.clearContext();

				ProblemDetail pd = problemFactory.build(HttpStatus.UNAUTHORIZED, "Token inválido", "invalid-token",
						request, ex);

				problemWriter.write(response, pd);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}
