package com.security.dkbt.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.dkbt.infrastructure.persistence.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	
    /*@Query("""
    		   SELECT u FROM UsuarioEntity u
    		   JOIN FETCH u.rol
    		   WHERE u.username = :username
    		     AND u.eliminado = false
    		     AND u.estado = true
    		""")
    Optional<UsuarioEntity> findAuthUser(@Param("username") String username);
    */
    Optional<UsuarioEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByCorreo(String correo);
}