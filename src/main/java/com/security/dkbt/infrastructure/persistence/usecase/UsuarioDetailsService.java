package com.security.dkbt.infrastructure.persistence.usecase;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.dkbt.config.jwt.JwtUserDetails;
import com.security.dkbt.infrastructure.persistence.entity.UsuarioEntity;
import com.security.dkbt.infrastructure.persistence.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UsuarioEntity user = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
		
		return new JwtUserDetails(user.getUsername(), user.getPasswordHash(), user.getIdEmpleado(), user.getAuthorities());
	}
}