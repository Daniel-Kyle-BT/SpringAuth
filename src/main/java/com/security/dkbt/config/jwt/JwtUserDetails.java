package com.security.dkbt.config.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final String username;
	private final String password;
    private final Long empleadoId;
    private final Collection<? extends GrantedAuthority> authorities;
    
    public static JwtUserDetails fromToken(
            String username,
            Long empleadoId,
            Collection<? extends GrantedAuthority> authorities) {

        return new JwtUserDetails(username, null, empleadoId, authorities);
    }

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
