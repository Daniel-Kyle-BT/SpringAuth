package com.security.dkbt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.dkbt.entity.UsuarioEntity;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	
    @Query("""
    		   SELECT u FROM UsuarioEntity u
    		   JOIN FETCH u.rol
    		   WHERE u.username = :username
    		     AND u.eliminado = false
    		     AND u.estado = true
    		""")
    Optional<UsuarioEntity> findAuthUser(@Param("username") String username);
}