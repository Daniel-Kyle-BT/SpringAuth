package com.security.dkbt.config.jwt;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.dkbt.exception.problem.ProblemFactory;
import com.security.dkbt.exception.problem.ProblemWriter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

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

				jwtUtil.validarToken(token);

				String username = jwtUtil.obtenerUsername(token);
				Long empleadoId = jwtUtil.obtenerEmpleadoId(token);

				Collection<? extends GrantedAuthority> authorities = jwtUtil.obtenerRoles(token).stream()
						.map(SimpleGrantedAuthority::new).toList();

				JwtUserDetails user = new JwtUserDetails(username, empleadoId, authorities);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
						authorities);

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

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getServletPath().startsWith("/api/auth/");
	}
}
