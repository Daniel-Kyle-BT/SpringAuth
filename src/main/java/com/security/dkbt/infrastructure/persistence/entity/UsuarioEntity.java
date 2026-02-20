package com.security.dkbt.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usuarios", indexes = { @Index(name = "idx_username", columnList = "username"),
		@Index(name = "idx_id_empleado", columnList = "id_empleado") })
public class UsuarioEntity implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "id_empleado", nullable = false, unique = true)
	private Long idEmpleado;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_rol", nullable = false)
	private RolEntity rol;

	@Column(name = "correo", nullable = false, unique = true, length = 100)
	private String correo;

	@Column(name = "username", nullable = false, unique = true, length = 100)
	private String username;

	@Column(name = "password_hash", nullable = false, length = 250)
	private String passwordHash;

	@Column(name = "estado", nullable = false)
	private Boolean estado = true;

	@Column(name = "fecha_registro", nullable = false, updatable = false)
	private LocalDateTime fechaRegistro;

	@Column(name = "fecha_eliminado")
	private LocalDateTime fechaEliminado;

	@PrePersist
	public void prePersist() {
		this.fechaRegistro = LocalDateTime.now();
	}

	// ==========================
	// SPRING SECURITY
	// ==========================

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(rol.getNombre()));
	}

	@Override
	public String getPassword() {
		return passwordHash;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public String getRolNombre() {
	    return rol.getNombre();
	}

	@Override
	public boolean isAccountNonExpired() {
		return estado;
	}

	@Override
	public boolean isAccountNonLocked() {
		return estado;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return estado;
	}

	@Override
	public boolean isEnabled() {
		return estado;
	}
}