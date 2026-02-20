package com.security.dkbt.config.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final String username;
    private final Long empleadoId;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(
            String username,
            Long empleadoId,
            Collection<? extends GrantedAuthority> authorities) {

        this.username = username;
        this.empleadoId = empleadoId;
        this.authorities = authorities;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override public String getPassword() { return null; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
